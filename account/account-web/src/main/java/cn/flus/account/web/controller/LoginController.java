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

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.service.AccountUserService;

/**
 * login
 * 
 * @author zhouxing
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    @Autowired
    private AccountUserService accountUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam(value = "loginname", required = true) String loginname,
                                  @RequestParam(value = "password", required = true) String password, ModelMap model) {
        AccountUserEntity accountUserEntity = accountUserService.getByLoginname(loginname);
        boolean s = accountUserService.checkPassword(accountUserEntity, password);
        System.out.println(s);
        // 记录session
        return null;
    }
}
