package cn.flus.viewer.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.flus.viewer.core.service.AuthenticationService;
import cn.flus.viewer.core.service.FileUploadService;
import cn.flus.viewer.web.dto.StandardResponse;

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

        // 查看转换进度
        int progress = fileUploadService.fetchConvertProgress(urn);
        model.addAttribute("progress", progress);

        String token = authenticationService.fetchAccessToken();
        model.addAttribute("token", token);

        model.addAttribute("urn", urn);
        return new ModelAndView("preview");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public StandardResponse<String> upload(@RequestParam(value = "file", required = false) MultipartFile file,
                                           ModelMap model) {

        String urn = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            urn = fileUploadService.uploadAndConvert(inputStream, file.getOriginalFilename());
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

        if (StringUtils.isBlank(urn)) {
            return new StandardResponse<String>("error", "a");
        }
        return new StandardResponse<String>(urn);
    }
}
