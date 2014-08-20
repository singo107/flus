package cn.flus.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.service.AccountUserService;

/**
 * Homepage
 * 
 * @author zhouxing
 */
@Controller
@RequestMapping("/")
public class SignupController extends BaseController {

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
}
