package jp.co.stnet.cms.config;

import jakarta.annotation.PostConstruct;
import jp.co.stnet.cms.base.application.service.ApiAuthenticationUserDetailServiceImpl;
import jp.co.stnet.cms.base.application.service.PermissionRoleService;
import jp.co.stnet.cms.common.authentication.ApiPreAuthenticatedProcessingFilter;
import jp.co.stnet.cms.common.authentication.FormLoginDaoAuthenticationProvider;
import jp.co.stnet.cms.common.authentication.FormLoginUsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;
    private final PermissionRoleService permissionRoleService;
    private final ApiAuthenticationUserDetailServiceImpl apiAuthenticationUserDetailService;

    /**
     * API(/api/**)のセキュリティ設定
     * (参考)https://www.baeldung.com/spring-boot-shared-secret-authentication
     * (参考)https://www.danvega.dev/blog/multiple-spring-security-configs
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // 対象URL
                .securityMatcher("/api/**")

                // API独自認証(API-KEY)を追加
                .addFilter(preAuthenticatedProcessingFilter())

                // apiの実行には認証が必要
                .authorizeHttpRequests(x -> x
                        .anyRequest()
                        .authenticated()
                )

                // セッションを使用しない
                .sessionManagement(x -> x
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // CSRF対策機能の無効化
                .csrf(AbstractHttpConfigurer::disable)
        ;

        return http.build();
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() {
        var filter = new ApiPreAuthenticatedProcessingFilter();
        filter.setAuthenticationManager(apiAuthenticationManager());
        return filter;
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(apiAuthenticationUserDetailService);
        provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
        return provider;
    }

    @Bean
    AuthenticationManager apiAuthenticationManager() {
        return new ProviderManager(preAuthenticatedAuthenticationProvider());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Web画面のセキュリティ設定
     */
    @Bean
    @Order(1)
    public SecurityFilterChain formLoginSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(x -> x
                        // 匿名ユーザのアクセス可
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/account/create").permitAll()
                        .requestMatchers("/reissue/**").permitAll()
                        .requestMatchers("/app/**").permitAll()
                        .requestMatchers("/AdminLTE/**").permitAll()
                        .requestMatchers("/plugins/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // 管理者のみアクセス可
                        .requestMatchers("/unlock/**").hasAnyRole("ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")

                        // デフォルトでは認証が必要
                        .anyRequest().authenticated()
                )

                // ログイン画面設定
                .formLogin(x -> x
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )

                // ログアウト設定
                .logout(x -> x
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                // カスタムフィルターの追加
                .addFilterAt(formLoginUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

        ;

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormLoginDaoAuthenticationProvider(permissionRoleService, userDetailsService, passwordEncoder);
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        var a = authenticationConfiguration.getAuthenticationManager();
        var b = new ProviderManager(authenticationProvider());
        b.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher());

        return b;
    }


    @Bean
    public FormLoginUsernamePasswordAuthenticationFilter formLoginUsernamePasswordAuthenticationFilter() throws Exception {
        var filter = new FormLoginUsernamePasswordAuthenticationFilter();
        //
        filter.setAuthenticationManager(authenticationManager());
        //
        filter.setSessionAuthenticationStrategy(new CompositeSessionAuthenticationStrategy(sessionAuthenticationStrategies()));
        // ログイン失敗時の遷移先
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error=true"));
        // ログイン前のリクエスト(URL)を保持し、ログイン成功後に要求されたURLに遷移する
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        // セキュリティコンテキストの保存方法
        filter.setSecurityContextRepository(new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        ));

        return filter;
    }

    /**
     * 複数のセッション管理機能を混在させる
     *
     * @return List<SessionAuthenticationStrategy>
     */
    private List<SessionAuthenticationStrategy> sessionAuthenticationStrategies() {
        List<SessionAuthenticationStrategy> strategies = new ArrayList<>();
        strategies.add(new CsrfAuthenticationStrategy(new HttpSessionCsrfTokenRepository()));
        strategies.add(new SessionFixationProtectionStrategy());
        strategies.add(concurrentSessionControlAuthenticationStrategy());
        strategies.add(new RegisterSessionAuthenticationStrategy(sessionRegistry));
        return strategies;
    }

    /**
     * セッション管理設定
     *
     * @return sessionControlAuthenticationStrategy
     */
    private ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
        var sessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        sessionControlAuthenticationStrategy.setMaximumSessions(1);
        sessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);
        return sessionControlAuthenticationStrategy;
    }

    // -----------------------------------------------------------------------------------------------------------------

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
        return filter;
    }
}
