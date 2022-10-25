package jp.co.stnet.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    public TransactionTokenInterceptor transactionTokenIntercepter() {
        return new TransactionTokenInterceptor(1);
    }

    /**
     * トランザクショントークン
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionTokenIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/resources/**");
    }

}