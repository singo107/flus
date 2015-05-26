package cn.flus.account.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.flus.account.core.service.AccountUserService;

/**
 * login
 * 
 * @author zhouxing
 */
@Controller
@RequestMapping("/")
public class SignoutController extends BaseController {

    @Autowired
    private AccountUserService accountUserService;

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public ModelAndView signinPage(@RequestParam(value = "dest", required = false) String dest,
                                   HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("cuser");
        if (dest == null || dest.trim().length() <= 0) {
            return new ModelAndView(new RedirectView("index"));
        } else {
            return new ModelAndView(new RedirectView(dest));
        }
    }
}