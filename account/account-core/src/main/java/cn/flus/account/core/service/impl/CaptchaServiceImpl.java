package cn.flus.account.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.service.CaptchaService;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {

    private static final Logger logger             = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private ImageCaptchaService imageCaptchaService;

    private final static String CAPTCHA_IMAGE_TYPE = "jpg";

    @PostConstruct
    public void init() {
        imageCaptchaService = new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(),
                                                                       new WoodCaptchaEngine(), 180, 100000, 75000);
    }

    @Override
    public byte[] generateCaptcha(String captchaKey) {
        Assert.hasText(captchaKey);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = imageCaptchaService.getImageChallengeForID(captchaKey, Locale.SIMPLIFIED_CHINESE);
        try {
            ImageIO.write(image, CAPTCHA_IMAGE_TYPE, outputStream);
        } catch (IOException e) {
            logger.error("generate captcha failed.", e);
        }
        return outputStream.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String code) {
        Assert.hasText(captchaKey);
        Assert.hasText(code);
        return imageCaptchaService.validateResponseForID(captchaKey, code);
    }
}
