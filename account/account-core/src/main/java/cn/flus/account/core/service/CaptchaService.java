package cn.flus.account.core.service;

/**
 * 图形校验码
 * 
 * @author singo
 */
public interface CaptchaService {

    /**
     * 生成图形验证码，返回图形的字节码
     * 
     * @param captchaKey
     * @return
     */
    byte[] generateCaptcha(String captchaKey);

    /**
     * 校验图形验证码
     * 
     * @param captchaKey
     * @param captchaCode
     * @return
     */
    boolean validateCaptcha(String captchaKey, String captchaCode);

}
