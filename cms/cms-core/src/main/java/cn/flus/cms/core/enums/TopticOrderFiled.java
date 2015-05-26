package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoux
 */
public enum TopticOrderFiled {

    VC("viewCount"), CC("commentsCount"), PC("praiseCount"), CT("createTime"), PT("publishTime"), RT("lastReplyTime");

    private String code;

    private TopticOrderFiled(String code) {
        this.code = code;
    }

    private static final Map<String, TopticOrderFiled> map = new HashMap<String, TopticOrderFiled>();

    static {
        map.put(TopticOrderFiled.VC.getCode(), TopticOrderFiled.VC);
        map.put(TopticOrderFiled.CC.getCode(), TopticOrderFiled.CC);
        map.put(TopticOrderFiled.PC.getCode(), TopticOrderFiled.PC);
        map.put(TopticOrderFiled.CT.getCode(), TopticOrderFiled.CT);
        map.put(TopticOrderFiled.PT.getCode(), TopticOrderFiled.PT);
        map.put(TopticOrderFiled.RT.getCode(), TopticOrderFiled.RT);
    }

    public String getCode() {
        return code;
    }

    public static TopticOrderFiled parse(String code) {
        TopticOrderFiled s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }

}
