package cn.flus.account.web.filter;

public class SigninRequired {

    private String  pattern;
    private String  method;
    private boolean required;

    public SigninRequired() {
    }

    public SigninRequired(String pattern, String method, boolean required) {
        this.pattern = pattern;
        this.method = method;
        this.required = required;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "SigninRequired [pattern=" + pattern + ", method=" + method + ", required=" + required + "]";
    }

}
