package jp.co.stnet.cms.common.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

import java.util.ArrayList;

@Configuration
public class RequestDataValueProcessorPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    /**
     * トランザクショントークンとCSRFトークンの共存させる設定
     *
     * @param registry the bean definition registry used by the application context
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        var values = new ArrayList<RequestDataValueProcessor>();
        values.add(new TransactionTokenRequestDataValueProcessor());
        values.add(new CsrfRequestDataValueProcessor());

        var cav = new ConstructorArgumentValues();
        cav.addGenericArgumentValue(values);
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(CompositeRequestDataValueProcessor.class, cav, null);

        registry.removeBeanDefinition("requestDataValueProcessor");
        registry.registerBeanDefinition("requestDataValueProcessor", rootBeanDefinition);
    }
}
