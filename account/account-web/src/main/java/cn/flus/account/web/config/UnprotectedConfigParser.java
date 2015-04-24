package cn.flus.account.web.config;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class UnprotectedConfigParser extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        super.doParse(element, parserContext, builder);
        List<Element> requestPatternList = DomUtils.getChildElementsByTagName(element, "requestPattern");
        ManagedList<Object> nestedFiles = new ManagedList<Object>();
        ParserContext nestedCtx = new ParserContext(parserContext.getReaderContext(), parserContext.getDelegate(),
                                                    builder.getBeanDefinition());
        if (requestPatternList.size() > 0) {
            RequestPatternParser requestPatternParser = new RequestPatternParser();
            for (Element e : requestPatternList) {
                nestedFiles.add(requestPatternParser.parse(e, nestedCtx));
            }
        }
        builder.addPropertyValue("unprotected", nestedFiles);
    }

    @Override
    protected Class<UnprotectedConfig> getBeanClass(Element element) {
        return UnprotectedConfig.class;
    }

}
