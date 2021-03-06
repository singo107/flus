package cn.flus.account.core.utils;

import java.util.regex.Pattern;

/**
 * 用户名、手机号、邮箱的字符校验工具类
 * 
 * @author singo
 */
public class LoginnameUtils {

    // 手机号码的正则
    private static final String MOBILE_PHONE_REGEX_CN = "^1\\d{10}$";

    // 邮箱的正则
    private static final String EMAIL_REGEX           = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // 普通用户名的正则（字母、数字、下划线组成，2-64个字符）
    private static final String NORMAL_NAME_REGEX     = "^[A-Za-z][A-Za-z0-9_]{1,63}$";

    private static Pattern      MOBILE_PHONE_PATTERN_CN;
    private static Pattern      EMAIL_PATTERN;
    private static Pattern      NORMAL_NAME_PATTERN;

    static {
        MOBILE_PHONE_PATTERN_CN = Pattern.compile(MOBILE_PHONE_REGEX_CN);
        EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        NORMAL_NAME_PATTERN = Pattern.compile(NORMAL_NAME_REGEX);
    }

    // 判断用户名的合法性
    public static boolean check(String value) {
        return isMobile(value) || isEmail(value) || isNormalName(value);
    }

    public static boolean isMobile(String value) {
        if (value == null || value.length() <= 0) {
            return false;
        }
        return MOBILE_PHONE_PATTERN_CN.matcher(value).matches();
    }

    public static boolean isEmail(String value) {
        if (value == null || value.length() <= 0) {
            return false;
        }
        return EMAIL_PATTERN.matcher(value).matches();
    }

    public static boolean isNormalName(String value) {
        if (value == null || value.length() <= 0) {
            return false;
        }
        return NORMAL_NAME_PATTERN.matcher(value).matches();
    }
}
