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
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.web.utils.SigninExecutor;

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
    private SigninExecutor     signinExecutor;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signinPage(@RequestParam(value = "dest", required = false) String dest,
                                   HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.addAttribute("dest", dest);
        return new ModelAndView("signin");
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView signinPagePost(@RequestParam(value = "loginname", required = true) String loginname,
                                       @RequestParam(value = "password", required = true) String password,
                                       @RequestParam(value = "dest", required = false) String dest,
                                       @RequestParam(value = "error", required = false) String error, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) {

        // 根据用户名获取当前用户的信息
        AccountUserEntity accountUserEntity = accountUserService.getByLoginname(loginname);
        if (accountUserEntity == null) {
            return new ModelAndView(new RedirectView("signin?error=1"));
        }

        // 检查密码是否正确
        boolean match = accountUserService.checkPassword(accountUserEntity, password);
        if (!match) {
            return new ModelAndView(new RedirectView("signin?error=2"));
        }

        // 登录
        signinExecutor.signin(accountUserEntity, response);

        // 跳转
        String redirectUrl = null;
        if (dest == null || dest.length() <= 0) {
            redirectUrl = "index";
        } else {
            redirectUrl = dest;
        }
        return new ModelAndView(new RedirectView(redirectUrl));
    }
}
