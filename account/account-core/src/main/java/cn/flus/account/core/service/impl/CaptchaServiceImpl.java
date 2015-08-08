package cn.flus.account.core.service.impl;

import cn.flus.account.core.service.CaptchaService;

public class CaptchaServiceImpl implements CaptchaService {

    @Override
    public byte[] generateCaptcha(String captchaKey) {
        return null;
    }

    @Override
    public boolean validateCaptcha(String captchaKey) {
        return false;
    }

}
