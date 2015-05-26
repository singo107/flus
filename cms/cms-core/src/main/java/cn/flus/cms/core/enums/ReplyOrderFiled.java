package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoux
 */
public enum ReplyOrderFiled {

    PC("praiseCount"), CT("createTime");

    private String code;

    private ReplyOrderFiled(String code) {
        this.code = code;
    }

    private static final Map<String, ReplyOrderFiled> map = new HashMap<String, ReplyOrderFiled>();

    static {
        map.put(ReplyOrderFiled.PC.getCode(), ReplyOrderFiled.PC);
        map.put(ReplyOrderFiled.CT.getCode(), ReplyOrderFiled.CT);
    }

    public String getCode() {
        return code;
    }

    public static ReplyOrderFiled parse(String code) {
        ReplyOrderFiled s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }

}
