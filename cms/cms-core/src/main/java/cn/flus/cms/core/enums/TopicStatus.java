package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 主题的状态
 * 
 * @author singo
 */
public enum TopicStatus {

    DRAFT(0, "草稿"), OFFLINE(1, "下线"), ONLINE(2, "上线");

    private Integer value;
    private String  display;

    private TopicStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    private static final Map<Integer, TopicStatus> map = new HashMap<Integer, TopicStatus>();

    static {
        map.put(TopicStatus.DRAFT.getValue(), TopicStatus.DRAFT);
        map.put(TopicStatus.OFFLINE.getValue(), TopicStatus.OFFLINE);
        map.put(TopicStatus.ONLINE.getValue(), TopicStatus.ONLINE);
    }

    public Integer getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }

    public static Map<Integer, TopicStatus> getMap() {
        return map;
    }

    public static TopicStatus parse(Integer code) {
        TopicStatus s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
