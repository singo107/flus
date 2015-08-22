package cn.flus.account.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.exceptions.ExceedMaxValidateException;
import cn.flus.account.core.service.CaptchaService;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {

    private static final Logger           logger                 = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private Producer                      producer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${captcha.mock}")
    private boolean                       mock;
    private final static String           MOCK_CODE              = "1111";

    private static final String           CAPTCHA_REDIS_KEY_PREX = "captcha.";
    private static final String           CAPTCHA_CODE_KEY       = "code";
    private static final String           CAPTCHA_TIMES_KEY      = "times";
    private final static int              CAPTCHA_EXPIRE_TIME    = 5 * 60 * 1000;
    private final static int              CAPTCHA_ERROR_TIMES    = 3;

    @PostConstruct
    public void init() {

        // 验证码配置
        Properties prop = new Properties();
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "abcdefghijklmnopqrstuvwxyz0123456789");
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "50,50,50");
        prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "255,255,255");
        prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, "255,255,255");
        prop.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "100");
        prop.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
        prop.setProperty(Constants.KAPTCHA_BORDER, "no");
        prop.setProperty(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(prop);
        this.producer = config.getProducerImpl();

        // redis配置
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    }

    @Override
    public byte[] generateCaptcha(String captchaKey) {

        Assert.hasText(captchaKey);

        // 生成验证码code
        String code = null;
        if (mock) {
            code = MOCK_CODE;
        } else {
            code = producer.createText();
        }
        logger.debug("captcha code: " + code);

        // 存储验证码 & 校验次数
        redisTemplate.opsForHash().put(redisKey(captchaKey), CAPTCHA_CODE_KEY, code);
        redisTemplate.opsForHash().put(redisKey(captchaKey), CAPTCHA_TIMES_KEY, Integer.toString(0));
        redisTemplate.expire(redisKey(captchaKey), CAPTCHA_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        // 生成验证码图片
        ImageIO.setUseCache(false);
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            logger.error("generate captcha failed.", e);
        }
        return outputStream.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captchaCode) {

        Assert.hasText(captchaKey);
        Assert.hasText(captchaCode);

        // 判断captchaKey是否存在
        if (redisTemplate.opsForHash().size(redisKey(captchaKey)) <= 0) {
            logger.warn("captcha key not exist: " + captchaKey);
            return false;
        }

        // 获取校验错误次数
        int times = Integer.parseInt((String) redisTemplate.opsForHash().get(redisKey(captchaKey), CAPTCHA_TIMES_KEY));
        logger.debug("captcha error times: " + times);
        if (times >= CAPTCHA_ERROR_TIMES) {
            redisTemplate.delete(redisKey(captchaKey));
            logger.warn("captcha error times exceed max: " + CAPTCHA_ERROR_TIMES);
            throw new ExceedMaxValidateException("captcha error times exceed max: " + CAPTCHA_ERROR_TIMES);
        }

        // 校验正确?
        String code = (String) redisTemplate.opsForHash().get(redisKey(captchaKey), CAPTCHA_CODE_KEY);
        if (captchaCode.equalsIgnoreCase(code)) {
            return true;
        }

        // 校验错误次数加1
        redisTemplate.opsForHash().put(redisKey(captchaKey), CAPTCHA_TIMES_KEY, Integer.toString(++times));
        return false;
    }

    private String redisKey(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return CAPTCHA_REDIS_KEY_PREX + val;
    }
}
