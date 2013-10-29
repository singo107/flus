package cn.flus.account.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Homepage
 * 
 * @author zhouxing 2013-10-18
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root(ModelMap model) {
        return new ModelAndView(new RedirectView("index"));
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {
        model.addAttribute("msg", "Singo Hello world!");
        return new ModelAndView("index");
    }
}
