package cn.flus.account.core.utils;

/**
 * 判断密码强度工具类
 * 
 * @author zhouxing
 */
public class PasswordStrength {

    public static final String effectiveChars         = "0123456789" + "abcdefghijklmnopqrstuvwxyz"
                                                        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                                        + "`~!@#$%^&*()-_=+[{]};:'\"\\|,<.>/?";

    // 不合法
    public static final int    ILLEGAL                = 0;

    // 低
    public static final int    BAD                    = 1;

    // 中
    public static final int    GOOD                   = 2;

    // 高
    public static final int    STRONG                 = 3;

    // 默认合法的级别
    public static final int    DEFAULT_VALID_STRENGTH = BAD;

    /**
     * 计算密码的强度
     * 
     * @param password
     * @return
     */
    public static int calStrength(String password) {

        // 校验字符合法性
        if (password == null || password.length() < 6) {
            return ILLEGAL;
        }
        for (int i = 0; i < password.length(); i++) {
            if (effectiveChars.indexOf(password.charAt(i)) == -1) {
                return ILLEGAL;
            }
        }

        // 初始化密码分数
        int score = 0;

        // password length
        score += password.length() * 4;
        score += (checkRepetition(1, password).length() - password.length()) * 1;
        score += (checkRepetition(2, password).length() - password.length()) * 1;
        score += (checkRepetition(3, password).length() - password.length()) * 1;
        score += (checkRepetition(4, password).length() - password.length()) * 1;

        // password has 3 numbers
        if (password.matches("^(.*[0-9].*[0-9].*[0-9].*)$")) {
            score += 5;
        }

        // password has 2 sybols
        if (password.matches("^(.*[`~!@#$%^&*()\\-_=+\\[{\\]};:'\"\\|,<\\.>/?\\\\].*[`~!@#$%^&*()\\-_=+\\[{\\]};:'\"\\|,<\\.>/?\\\\].*)$")) {
            score += 5;
        }

        // password has Upper and Lower chars
        if (password.matches("^(.*[a-z].*[A-Z].*)|(.*[A-Z].*[a-z].*)$")) {
            score += 10;
        }

        // password has number and chars
        if (password.matches("^(.*[a-zA-Z].*)$") && password.matches("^(.*[0-9].*)$")) {
            score += 15;
        }

        // password has number and symbol
        if (password.matches("^(.*[`~!@#$%^&*()\\-_=+\\[{\\]};:'\"\\|,<\\.>/?\\\\].*)$")
            && password.matches("^(.*[0-9].*)$")) {
            score += 15;
        }

        // password has char and symbol
        if (password.matches("^(.*[`~!@#$%^&*()\\-_=+\\[{\\]};:'\"\\|,<\\.>/?\\\\].*)$")
            && password.matches("^(.*[a-zA-Z].*)$")) {
            score += 15;
        }

        // password is just a numbers or chars
        if (password.matches("^\\w+$") || password.matches("^\\d+$")) {
            score -= 10;
        }

        // 根据分数得出等级
        if (score < 34) return BAD;
        if (score < 68) return GOOD;
        return STRONG;
    }

    private static String checkRepetition(int pLen, String str) {
        String res = "";
        int i = 0;
        while (i < str.length()) {
            boolean repeated = true;
            int j = 0;
            while (j < pLen && (j + i + pLen) < str.length()) {
                repeated = repeated && (str.charAt(j + i) == str.charAt(j + i + pLen));
                j++;
            }
            if (j < pLen) repeated = false;
            if (repeated) {
                i += (pLen - 1);
            } else {
                res += str.charAt(i);
            }
            i++;
        }
        return res;
    }

    public static void main(String[] args) {
        String p = "123\\456\\89";
        System.out.println(p);
        System.out.println(calStrength(p));
    }
}
