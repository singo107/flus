package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoux
 */
public enum OrderType {

    DESC("desc", "从高到低"), ASC("asc", "从低到高");

    private String code;
    private String display;

    private OrderType(String code, String display) {
        this.code = code;
        this.display = display;
    }

    private static final Map<String, OrderType> map = new HashMap<String, OrderType>();

    static {
        map.put(OrderType.DESC.getCode(), OrderType.DESC);
        map.put(OrderType.ASC.getCode(), OrderType.ASC);
    }

    public String getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }

    public static OrderType parse(String code) {
        OrderType s = map.get(code);
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
        OrderType t = parse(code);
        if (t == null) {
            return DESC.getDisplay();
        }
        return t.getDisplay();
    }
}
