package cn.flus.account.core.service;

public interface CaptchaService {

    /**
     * 生成图形验证码
     * 
     * @param captchaKey
     * @return
     */
    byte[] generateCaptcha(String captchaKey);

    /**
     * 校验图形验证码
     * 
     * @param captchaKey
     * @return
     */
    boolean validateCaptcha(String captchaKey);

}
