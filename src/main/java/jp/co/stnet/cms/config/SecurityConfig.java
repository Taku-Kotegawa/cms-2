package jp.co.stnet.cms.config;

import jp.co.stnet.cms.base.application.service.ApiAuthenticationUserDetailServiceImpl;
import jp.co.stnet.cms.common.authentication.ApiPreAuthenticatedProcessingFilter;
import jp.co.stnet.cms.common.authentication.FormLoginDaoAuthenticationProvider;
import jp.co.stnet.cms.common.authentication.FormLoginUsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * API(/api/**)のセキュリティ設定
     * ※Web画面の設定より上に配置すること
     */
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilter(preAuthenticatedProcessingFilter())

                .antMatcher("/api/**")
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()

                // セッションを使用しない
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 例外処理
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())

                // CSRF対策機能の無効化
                .and()
                .csrf().disable();

        return http.build();
    }

    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
        AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter = new ApiPreAuthenticatedProcessingFilter();
        preAuthenticatedProcessingFilter.setAuthenticationManager(apiAuthenticationManager());
        return preAuthenticatedProcessingFilter;
    }

    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new ApiAuthenticationUserDetailServiceImpl());
        provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
        return provider;
    }

    AuthenticationManager apiAuthenticationManager() {
        return new ProviderManager(preAuthenticatedAuthenticationProvider());
    }

    /**
     * Web画面のセキュリティ設定
     */
    @Bean
    public SecurityFilterChain formLoginSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .addFilterAt(formLoginUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests()

                // 匿名ユーザのアクセス可
                .antMatchers("/login").permitAll()
                .antMatchers("/account/create").permitAll()
                .antMatchers("/reissue/**").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/AdminLTE/**").permitAll()
                .antMatchers("/plugins/**").permitAll()

                // 管理者のみアクセス可
                .antMatchers("/unlock/**").hasAnyRole("ADMIN")
                .antMatchers("/admin/impersonate/**").hasAnyRole("ADMIN")

                // デフォルトでは認証が必要
                .anyRequest()
                .authenticated();

        // ログイン画面設定
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password");

        // ログアウト設定
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }

    /**
     * ログイン画面のカスタマイズ
     */
    @Bean
    public AuthenticationProvider formLoginDaoAuthenticationProvider() {
        var provider = new FormLoginDaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * ログイン画面のカスタマイズ
     */
    private Filter formLoginUsernamePasswordAuthenticationFilter() throws Exception {
        var filter = new FormLoginUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error=true"));
        return filter;
    }

    /**
     * 非同期処理で認証情報を呼び出し先のオブジェクトに渡す
     */
    @PostConstruct
    public void enableAuthCtxOnSpawnedThreads() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    /**
     * ユーザ切り替え機能
     */
    @Bean
    public SwitchUserFilter switchUserFilter(UserDetailsService userDetailsService) {
        SwitchUserFilter filter = new SwitchUserFilter();
        filter.setUserDetailsService(userDetailsService);
        filter.setUsernameParameter("username");
        filter.setSwitchUserUrl("/admin/impersonate");
        filter.setExitUserUrl("/logout/impersonate");
        filter.setTargetUrl("/");
//        filter.afterPropertiesSet();
        return filter;
    }

    /**
     * ログイン中ユーザ一覧のための設定
     */
    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }


}
