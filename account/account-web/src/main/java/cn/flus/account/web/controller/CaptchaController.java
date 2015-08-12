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

import cn.flus.account.core.service.CaptchaService;

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
}
