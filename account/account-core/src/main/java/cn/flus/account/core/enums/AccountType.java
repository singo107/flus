package cn.flus.account.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoux
 */
public enum AccountType {

    NORMAL(1);

    private Integer code;

    private AccountType(Integer code) {
        this.code = code;
    }

    private static final Map<Integer, AccountType> map = new HashMap<Integer, AccountType>();

    static {
        map.put(AccountType.NORMAL.getCode(), AccountType.NORMAL);
    }

    public Integer getCode() {
        return code;
    }

    public static AccountType parse(Integer code) {
        AccountType s = map.get(code);
        if (s == null) {
            return null;
        }
        return s;
    }
}
