package cn.flus.account.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.flus.account.web.utils.SigninUtils;

/**
 * 首页
 * 
 * @author singo
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root(ModelMap model) {
        return new ModelAndView(new RedirectView("index"));
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {
        model.addAttribute("msg", "Hello world!");
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView info(ModelMap model) {
        model.addAttribute("user", SigninUtils.get());
        return new ModelAndView("info");
    }
}
