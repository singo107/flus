package cn.flus.cms.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 栏目的状态
 * 
 * @author singo
 */
public enum CategoryStatus {

    ENABLE(1, "已启用"), DISABLE(0, "已停用");

    private Integer value;
    private String  display;

    private CategoryStatus(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    private static final Map<Integer, CategoryStatus> map = new HashMap<Integer, CategoryStatus>();

    static {
        map.put(CategoryStatus.ENABLE.getValue(), CategoryStatus.ENABLE);
        map.put(CategoryStatus.DISABLE.getValue(), CategoryStatus.DISABLE);
    }

    public Integer getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }

    public static Map<Integer, CategoryStatus> getMap() {
        return map;
    }

    public static CategoryStatus parse(Integer code) {
        CategoryStatus s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
