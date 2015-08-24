package cn.flus.account.web.filter;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SigninRequiredReader extends DefaultHandler {

    private SigninRequired       signinRequired;

    private List<SigninRequired> list;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list = new ArrayList<SigninRequired>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (name.equals("request")) {

            String pattern = null;
            String method = null;
            boolean required = false;

            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    if (attributes.getQName(i).equals("pattern")) {
                        pattern = attributes.getValue(i);
                    } else if (attributes.getQName(i).equals("method")) {
                        method = attributes.getValue(i);
                    } else if (attributes.getQName(i).equals("required")) {
                        required = Boolean.getBoolean(attributes.getValue(i));
                    }
                }
            }
            signinRequired = new SigninRequired(pattern, method, required);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (name.equals("request")) {
            list.add(signinRequired);
        }
    }

    public List<SigninRequired> get() {
        return list;
    }

}
