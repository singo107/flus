package cn.flus.account.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoux
 */
public enum GenderType {

    MALE("m", "男"), FEMALE("f", "女");

    private String              code;
    private String              display;

    // 当性别匹配不了，则显示默认值
    private final static String DEFAULT_DISPLAY = "未知";

    private GenderType(String code, String display) {
        this.code = code;
        this.display = display;
    }

    private static final Map<String, GenderType> map = new HashMap<String, GenderType>();

    static {
        map.put(GenderType.MALE.getCode(), GenderType.MALE);
        map.put(GenderType.FEMALE.getCode(), GenderType.FEMALE);
    }

    public String getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }

    public static GenderType parse(String code) {
        GenderType s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }

    /**
     * 获取显示名称
     * 
     * @param code
     * @return
     */
    public static String display(String code) {
        GenderType t = parse(code);
        if (t == null) {
            return DEFAULT_DISPLAY;
        }
        return t.getDisplay();
    }

    public static String getCode(String display) {
        for (GenderType type : GenderType.values()) {
            if (type.getDisplay().equalsIgnoreCase(display)) {
                return type.getCode();
            }
        }
        return GenderType.MALE.getCode();
    }
}
