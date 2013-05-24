package cn.flus.blog.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		ModelMap map = new ModelMap();
		map.addAttribute("kg", "singo");

		return new ModelAndView("index", map);
	}
}
