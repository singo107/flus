package cn.flus.account.core.enums;

import cn.flus.account.core.utils.LoginnameUtils;

/**
 * 3种用户名类型
 * 
 * @author zhouxing
 */
public enum LoginnameType {

    /**
     * 普通用户名类型
     */
    NORMAL(null),

    /**
     * 邮箱类型<br />
     * mask位于低1位<br />
     * 0:未经过校验, 1:已校验
     */
    EMAIL(0x1),

    /**
     * 手机号类型<br />
     * mask位于低2位<br />
     * 0:未经过校验, 1:已校验
     */
    MOBILE(0x2);

    private Integer mask; // mask表示用户status的校验位

    private LoginnameType(Integer mask) {
        this.mask = mask;
    }

    public Integer getMask() {
        return mask;
    }

    /**
     * 根据用户名判断类型
     * 
     * @param value
     * @return
     */
    public static LoginnameType getByLoginname(String value) {
        if (LoginnameUtils.isMobile(value)) {
            return MOBILE;
        }
        if (LoginnameUtils.isEmail(value)) {
            return EMAIL;
        }
        if (LoginnameUtils.isNormalName(value)) {
            return NORMAL;
        }
        return null;
    }
}
