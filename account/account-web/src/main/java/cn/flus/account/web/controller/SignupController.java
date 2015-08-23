package cn.flus.account.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.exceptions.LoginnameInvalidException;
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.web.bean.StandardResponse;

/**
 * 注册
 * 
 * @author singo
 */
@Controller
@RequestMapping("/")
public class SignupController {

    @Autowired
    private AccountUserService accountUserService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupPage(ModelMap model) {
        AccountUserEntity accountUserEntity = accountUserService.signup("singo110@qq.com", "123qwe!@#");
        model.addAttribute("msg", accountUserEntity);
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signupPost(ModelMap model) {
        return null;
    }

    @RequestMapping(value = "/loginname/validate", method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse<String> validate(@RequestParam(value = "loginname", required = true) String loginname,
                                             HttpServletRequest request, HttpServletResponse response) {
        // 检查用户名是否存在
        boolean result = false;
        try {
            result = accountUserService.checkExist(loginname);
        } catch (LoginnameInvalidException e) {
            return new StandardResponse<String>("loginname.invalid", "用户名格式错误，请输入新的用户名！");
        }

        // 返回
        if (result) {
            return new StandardResponse<String>(loginname);
        } else {
            return new StandardResponse<String>("loginname.exist", "用户名已经存在，请直接登录！");
        }
    }
}
