package jp.co.stnet.cms.common.processor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.logging.Logger;

/**
 * 同一セッション内のリクエストの同期化
 *
 * @see <a href="https://macchinetta.github.io/server-guideline-thymeleaf/current/ja/ArchitectureInDetail/WebApplicationDetail/SessionManagement.html#session-management-how-to-extend-synchronizeonsession">同一セッション内のリクエストの同期化</a>
 */
@Slf4j
public class EnableSynchronizeOnSessionPostProcessor
        implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // NO-OP
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter) {
            RequestMappingHandlerAdapter adapter =
                    (RequestMappingHandlerAdapter) bean;
            log.info("enable synchronizeOnSession => {}", adapter);
            adapter.setSynchronizeOnSession(true);
        }
        return bean;
    }

}