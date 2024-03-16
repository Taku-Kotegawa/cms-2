package jp.co.stnet.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

}