package cn.flus.account.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.service.CaptchaService;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Service("captchaService")
public class KaptchaServiceImpl implements CaptchaService {

    private static final Logger logger             = LoggerFactory.getLogger(KaptchaServiceImpl.class);
    private DefaultKaptcha      captchaProducer;

    private final static String CAPTCHA_IMAGE_TYPE = "jpg";

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH, 250);
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, 250);
        captchaProducer = new DefaultKaptcha();
        captchaProducer.setConfig(new Config(properties));
    }

    @Override
    public byte[] generateCaptcha(String captchaKey) {

        Assert.hasText(captchaKey);

        String code = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
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
        return true;
    }
}
