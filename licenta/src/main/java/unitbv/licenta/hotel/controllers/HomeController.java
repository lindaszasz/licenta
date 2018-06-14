package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import unitbv.licenta.hotel.models.User;
import unitbv.licenta.hotel.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if(user.getActive() == 2) {
			modelAndView = new ModelAndView("index");
		}
		else
		{ 
			 modelAndView = new ModelAndView("admin/index");
		}
		
		//modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		//modelAndView.setViewName("admin/home");
		return modelAndView;	
	}

}
