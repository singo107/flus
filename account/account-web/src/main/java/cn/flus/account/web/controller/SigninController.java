package cn.flus.account.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.exceptions.ExceedMaxValidateException;
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.web.utils.SignExecutor;

/**
 * 登录
 * 
 * @author singo
 */
@Controller
@RequestMapping("/")
public class SigninController {

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private SignExecutor       signinExecutor;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signinPage(@RequestParam(value = "dest", required = false) String dest,
                                   @RequestParam(value = "error", required = false) String error,
                                   HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.addAttribute("dest", dest);
        model.addAttribute("error", error);
        return new ModelAndView("signin");
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView signinPagePost(@RequestParam(value = "loginname", required = true) String loginname,
                                       @RequestParam(value = "password", required = true) String password,
                                       @RequestParam(value = "rememberMe", required = false) String rememberMe,
                                       @RequestParam(value = "dest", required = false) String dest, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) {

        // 根据用户名获取当前用户的信息
        AccountUserEntity accountUserEntity = accountUserService.getByLoginname(loginname);
        if (accountUserEntity == null) {
            return new ModelAndView(new RedirectView("signin?error=loginname"));
        }

        // 检查密码是否正确
        boolean match = false;
        try {
            match = accountUserService.checkPassword(accountUserEntity, password);
        } catch (ExceedMaxValidateException e) {

            // 超过最大校验次数，账号冻结
            return new ModelAndView(new RedirectView("signin?error=freeze"));
        }
        if (!match) {

            // 密码错误
            return new ModelAndView(new RedirectView("signin?error=password"));
        }

        // 登录，记录会话
        signinExecutor.signin(accountUserEntity, response);

        // 记住登录状态，30天
        if (rememberMe != null && rememberMe.length() > 0) {
            signinExecutor.rememberMe(accountUserEntity, response, 30);
        } else {
            signinExecutor.rememberMe(accountUserEntity, response, 0);
        }

        // 跳转
        String redirectUrl = null;
        if (dest == null || dest.length() <= 0) {
            redirectUrl = "info";
        } else {
            redirectUrl = dest;
        }
        return new ModelAndView(new RedirectView(redirectUrl));
    }
}
