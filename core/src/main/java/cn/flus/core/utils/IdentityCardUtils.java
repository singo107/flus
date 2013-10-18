package cn.flus.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 身份证信息校验
 * 
 * @author zhoux 2013-3-26
 */
public class IdentityCardUtils {

    private static final Pattern  IDENTITY_CARD_PATTERN = Pattern.compile("^\\d{15}(\\d{2}[\\dx])?$",
                                                                          Pattern.CASE_INSENSITIVE);
    private static final String[] VALID_PROVINCE_CODES  = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32",
            "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
            "63", "64", "65", "71", "81", "82", "91"   };
    private static final int      WEIGHING[]            = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    private static final String   VERIGY_CODES[]        = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };

    /**
     * 校验身份证信息，包含省份，生日，最后一位校验位
     * 
     * @param credentialNumber
     * @return
     */
    public static boolean isValidIdentityCardNumber(String credentialNumber) {

        if (StringUtils.isBlank(credentialNumber)) {
            return false;
        }
        if (!IDENTITY_CARD_PATTERN.matcher(credentialNumber).matches()) {
            return false;
        }

        String provinceCode = credentialNumber.substring(0, 2);
        if (!isValidProvinceCode(provinceCode)) {
            return false;
        }

        String birthday;
        if (credentialNumber.length() == 18) {
            birthday = credentialNumber.substring(6, 14);
        } else {
            birthday = "19" + credentialNumber.substring(6, 12);
        }
        if (!isValidBirthday(birthday)) {
            return false;
        }

        if (credentialNumber.length() == 18 && !isValidVerifyCode(credentialNumber)) {
            return false;
        }

        return true;
    }

    private static boolean isValidProvinceCode(String provinceCode) {
        if (StringUtils.isBlank(provinceCode) || provinceCode.length() != 2) {
            return false;
        }
        for (String s : VALID_PROVINCE_CODES) {
            if (provinceCode.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidBirthday(String strBirthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false);
        try {
            Date birthday = sdf.parse(strBirthday);
            if (birthday.before(new Date())) {
                return true;
            }
        } catch (ParseException e) {
            // ignore
        }
        return false;
    }

    private static boolean isValidVerifyCode(String credentialNumber) {
        char[] chars = credentialNumber.substring(0, 17).toCharArray();
        int[] ints = new int[17];
        for (int i = 0; i < 17; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }
        int weightSum = 0;
        for (int i = 0; i < 17; i++) {
            weightSum += ints[i] * WEIGHING[i];
        }
        int remainder = weightSum % 11;
        String verifyCode = VERIGY_CODES[remainder];
        return verifyCode.equalsIgnoreCase(credentialNumber.substring(17));
    }
}
