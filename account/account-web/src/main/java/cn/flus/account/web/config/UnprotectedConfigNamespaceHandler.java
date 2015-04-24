package cn.flus.account.web.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class UnprotectedConfigNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("requestPattern", new RequestPatternParser());
        registerBeanDefinitionParser("unprotectedConfig", new UnprotectedConfigParser());
    }
}
