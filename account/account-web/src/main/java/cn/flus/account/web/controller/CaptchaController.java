package cn.flus.account.web.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.flus.account.core.exceptions.CaptchaExpireException;
import cn.flus.account.core.exceptions.ExceedMaxValidateException;
import cn.flus.account.core.service.CaptchaService;
import cn.flus.account.web.bean.StandardResponse;

/**
 * 图形验证码
 * 
 * @author singo
 */
@Controller
@RequestMapping("/")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) {

        // 生成图形验证码
        byte[] captchaBytes = captchaService.generateCaptcha(request.getSession().getId());

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
            IOUtils.write(captchaBytes, servletOutputStream);
        } catch (IOException e) {
        }
    }

    @RequestMapping(value = "/captcha/validate", method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse<String> validate(@RequestParam(value = "code", required = true) String code,
                                             HttpServletRequest request, HttpServletResponse response) {

        // 检查校验码是否正确
        boolean result = false;
        try {
            result = captchaService.validateCaptcha(request.getSession().getId(), code);
        } catch (CaptchaExpireException e1) {
            return new StandardResponse<String>("validate.expire", "验证码过期，请输入新的验证码！");
        } catch (ExceedMaxValidateException e2) {
            return new StandardResponse<String>("validate.times.exceed", "验证码错误次数过多，请输入新的验证码！");
        }

        // 返回
        if (result) {
            return new StandardResponse<String>(code);
        } else {
            return new StandardResponse<String>("captcha.code.incorrect", "验证码错误，请重新输入！");
        }
    }
}
