package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 回复的状态
 * 
 * @author singo
 */
public enum ReplyStatus {

    TOAUDIT(0, "待审核"), PASSED(1, "审核通过");

    private Integer value;
    private String  display;

    private ReplyStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    private static final Map<Integer, ReplyStatus> map = new HashMap<Integer, ReplyStatus>();

    static {
        map.put(ReplyStatus.TOAUDIT.getValue(), ReplyStatus.TOAUDIT);
        map.put(ReplyStatus.PASSED.getValue(), ReplyStatus.PASSED);
    }

    public Integer getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }

    public static Map<Integer, ReplyStatus> getMap() {
        return map;
    }

    public static ReplyStatus parse(Integer code) {
        ReplyStatus s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
