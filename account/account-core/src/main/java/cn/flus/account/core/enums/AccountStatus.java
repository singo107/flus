package cn.flus.account.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoux
 */
public enum AccountStatus {

    DELETED(0), AVAILABLE(1);

    private Integer code;

    private AccountStatus(Integer code) {
        this.code = code;
    }

    private static final Map<Integer, AccountStatus> map = new HashMap<Integer, AccountStatus>();

    static {
        map.put(AccountStatus.DELETED.getCode(), AccountStatus.DELETED);
        map.put(AccountStatus.AVAILABLE.getCode(), AccountStatus.AVAILABLE);
    }

    public Integer getCode() {
        return code;
    }

    public static AccountStatus parse(Integer code) {
        AccountStatus s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
