package cn.flus.account.web.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RequestPatternParser extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        super.doParse(element, parserContext, builder);
        builder.addPropertyValue("pattern", element.getAttribute("pattern"));
        builder.addPropertyValue("httpMethod", element.getAttribute("httpMethod"));
    }

    @Override
    protected Class<RequestPattern> getBeanClass(Element element) {
        return RequestPattern.class;
    }
}
