package jp.co.stnet.cms.config.terasoluna;

import jp.co.stnet.cms.common.processor.EnableSynchronizeOnSessionPostProcessor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.ResultMessagesLoggingInterceptor;
import org.terasoluna.gfw.web.codelist.CodeListInterceptor;
import org.terasoluna.gfw.web.exception.HandlerExceptionResolverLoggingInterceptor;
import org.terasoluna.gfw.web.exception.SystemExceptionResolver;
import org.terasoluna.gfw.web.logging.TraceLoggingInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;

/**
 * Terasolunaが提供するライブラリの有効化(SpringMVCを利用する場合)
 */
@Configuration
@ConditionalOnProperty(prefix = TerasolunaProperties.TERASOLUNA_PREFIX, name = "mvc.enabled", matchIfMissing = true)
public class TerasolunaWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    TerasolunaProperties terasolunaProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new TraceLoggingInterceptor());

        registry.addInterceptor(codeListInterceptor());

        registry.addInterceptor(transactionTokenIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/resources/**");
    }

    @Bean
    public CodeListInterceptor codeListInterceptor() {
        CodeListInterceptor codeListInterceptor = new CodeListInterceptor();
        codeListInterceptor.setCodeListIdPattern(terasolunaProperties.getMvc().getCodeListIdPattern());
        return codeListInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public SystemExceptionResolver systemExceptionResolver() {
        TerasolunaProperties.Mvc mvc = terasolunaProperties.getMvc();
        SystemExceptionResolver exceptionResolver = new SystemExceptionResolver();
        exceptionResolver.setOrder(3);
        exceptionResolver.setExceptionMappings(mvc.getExceptionMappings());
        exceptionResolver.setStatusCodes(mvc.getStatusCodes());
        exceptionResolver.setDefaultErrorView(mvc.getDefaultErrorView());
        exceptionResolver.setStatusCodes(mvc.getStatusCodes());
        return exceptionResolver;
    }

    @Bean
    public HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor(ExceptionLogger exceptionLogger) {
        HandlerExceptionResolverLoggingInterceptor interceptor = new HandlerExceptionResolverLoggingInterceptor();
        interceptor.setExceptionLogger(exceptionLogger);
        return interceptor;
    }

    @Bean
    public Advisor resultMessagesLoggingAdvisor(ExceptionLogger exceptionLogger) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))");
        ResultMessagesLoggingInterceptor interceptor = new ResultMessagesLoggingInterceptor();
        interceptor.setExceptionLogger(exceptionLogger);
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }

    // 同一セッション内のリクエストの同期化
    @Bean
    public EnableSynchronizeOnSessionPostProcessor enableSynchronizeOnSessionPostProcessor() {
        return new EnableSynchronizeOnSessionPostProcessor();
    }

    // トランザクショントークチェック
    @Bean
    public TransactionTokenInterceptor transactionTokenIntercepter() {
        return new TransactionTokenInterceptor(1);
    }

}
