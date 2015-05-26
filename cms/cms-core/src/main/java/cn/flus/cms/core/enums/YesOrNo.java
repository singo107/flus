package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author singo
 */
public enum YesOrNo {

    YES("y"), NO("n");

    private String code;

    private YesOrNo(String code) {
        this.code = code;
    }

    private static final Map<String, YesOrNo> map = new HashMap<String, YesOrNo>();

    static {
        map.put(YesOrNo.YES.getCode(), YesOrNo.YES);
        map.put(YesOrNo.NO.getCode(), YesOrNo.NO);
    }

    public String getCode() {
        return code;
    }

    public static YesOrNo parse(Integer code) {
        YesOrNo s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
