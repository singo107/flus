package cn.flus.viewer.web.controller;

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

import cn.flus.viewer.core.service.AuthenticationService;
import cn.flus.viewer.core.service.FileUploadService;

/**
 * 首页
 * 
 * @author singo
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileUploadService     fileUploadService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root(ModelMap model) {
        return new ModelAndView(new RedirectView("index"));
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public ModelAndView preview(@RequestParam(value = "urn", required = true) String urn, HttpServletRequest request,
                                HttpServletResponse response, ModelMap model) {

        String token = authenticationService.fetchAccessToken();
        model.addAttribute("token", token);

        model.addAttribute("urn", urn);
        return new ModelAndView("preview");
    }
}
