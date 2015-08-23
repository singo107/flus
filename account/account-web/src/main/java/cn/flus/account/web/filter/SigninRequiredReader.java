package cn.flus.account.web.filter;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SigninRequiredReader extends DefaultHandler {

    private String               nodeName = null;

    private List<RequestMatcher> unrequiredList;
    private RequestMatcher       requestMatcher;
    private RequestPattern       requestPattern;

    public SigninRequiredReader(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        unrequiredList = new ArrayList<RequestMatcher>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (name.equals(nodeName)) {
            requestPattern = new RequestPattern();
        }
        if (attributes != null && requestPattern != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (attributes.getQName(i).equals("pattern")) {
                    requestPattern.setPattern(attributes.getValue(i));
                } else if (attributes.getQName(i).equals("method")) {
                    requestPattern.setHttpMethod(attributes.getValue(i));
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (name.equals(nodeName)) {
            if (requestPattern.getHttpMethod() == null || requestPattern.getHttpMethod().length() <= 0) {
                requestMatcher = new RequestMatcher(requestPattern.getPattern());
            } else {
                requestMatcher = new RequestMatcher(requestPattern.getPattern(), requestPattern.getHttpMethod());
            }
            unrequiredList.add(requestMatcher);
        }
    }

    public List<RequestMatcher> getUnrequiredList() {
        return unrequiredList;
    }

}
