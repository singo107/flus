package cn.flus.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查密码强度
 * 
 * @author zhouxing
 */
public class PasswordStrength {

    private final static int WEAK   = 1; // 低
    private final static int MEDIUM = 2; // 中
    private final static int STRONG = 3; // 高

    private int passwordStrength(String password) {

        if (password == null || password.length() < 6) {
            return WEAK;
        }

        // 初始分数
        int score = 0;

        // 密码长度
        score += password.length() * 4;

        // 检查重复
        score += (checkRepetition(1, password).length() - password.length());
        score += (checkRepetition(2, password).length() - password.length());
        score += (checkRepetition(3, password).length() - password.length());
        score += (checkRepetition(4, password).length() - password.length());

        // 包含3个数字
        Pattern p1 = Pattern.compile("^(.*[0-9].*[0-9].*[0-9].*)$");
        Matcher m1 = p1.matcher(password);
        if (m1.matches()) {
            score += 5;
        }

        // 包含2个特殊符号
        Pattern p2 = Pattern.compile("^(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~].*)$");
        Matcher m2 = p2.matcher(password);
        if (m2.matches()) {
            score += 5;
        }

        // 包含大写和小写字母
        Pattern p3 = Pattern.compile("^(.*[a-z].*[A-Z].*)|(.*[A-Z].*[a-z].*)$");
        Matcher m3 = p3.matcher(password);
        if (m3.matches()) {
            score += 10;
        }

        // 既包含字母又包含数字
        Pattern p4 = Pattern.compile("[a-zA-Z]");
        Matcher m4 = p4.matcher(password);
        Pattern p5 = Pattern.compile("[0-9]");
        Matcher m5 = p5.matcher(password);
        if (m4.find() && m5.find()) {
            score += 15;
        }

        // 既包含特殊符号又包含数字
        Pattern p6 = Pattern.compile("[!,@,#,$,%,^,&,*,?,_,~]");
        Matcher m6 = p6.matcher(password);
        Pattern p7 = Pattern.compile("[0-9]");
        Matcher m7 = p7.matcher(password);
        if (m6.find() && m7.find()) {
            score += 15;
        }

        // 既包含字母又包含特殊符号
        Pattern p8 = Pattern.compile("[a-zA-Z]");
        Matcher m8 = p8.matcher(password);
        Pattern p9 = Pattern.compile("[!,@,#,$,%,^,&,*,?,_,~]");
        Matcher m9 = p9.matcher(password);
        if (m8.find() && m9.find()) {
            score += 15;
        }

        // 仅包含字母或仅包含数字
        Pattern p10 = Pattern.compile("^([a-zA-Z])+$");
        Matcher m10 = p10.matcher(password);
        Pattern p11 = Pattern.compile("^([0-9])+$");
        Matcher m11 = p11.matcher(password);
        if (m10.matches() || m11.matches()) {
            score -= 10;
        }

        if (score < 0) score = 0;
        if (score > 100) score = 100;
        if (score < 34) return WEAK;
        if (score < 68) return MEDIUM;
        return STRONG;
    }

    // checkRepetition(1,'aaaaaaabcbc') = 'abcbc'
    // checkRepetition(2,'aaaabcbc') = 'aabc'
    // checkRepetition(3,'aaaaaaaaabcdbcd') = 'aaabcd'
private String checkRepetition(int pLen, String str) {
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
        System.out.println(new PasswordStrength().passwordStrength("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }
}
