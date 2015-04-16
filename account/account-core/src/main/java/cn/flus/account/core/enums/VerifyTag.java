package cn.flus.account.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author singo
 */
public enum VerifyTag {

    NOT_VERIFIED("n"), VERIFIED("y");

    private String code;

    private VerifyTag(String code) {
        this.code = code;
    }

    private static final Map<String, VerifyTag> map = new HashMap<String, VerifyTag>();

    static {
        map.put(VerifyTag.NOT_VERIFIED.getCode(), VerifyTag.NOT_VERIFIED);
        map.put(VerifyTag.VERIFIED.getCode(), VerifyTag.VERIFIED);
    }

    public String getCode() {
        return code;
    }

    public static VerifyTag parse(Integer code) {
        VerifyTag s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
