package com.ctet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = {"/welcome" })
	public String home(Model model) {
		System.out.println("test.............................................");
		return "home";
	}
	
	@PostMapping( value = "/authenticateTheUser"  )
	public String showNewProductForm(Model model) {
		System.out.println("test.............................................");
		return "403";
	}

	
	@RequestMapping("/test")
	public String test(Model model) {
		return "test404";
	}

	@RequestMapping("/login_success")
	@ResponseBody
	public String login_success() {
		return "Log in success";
	}
	
//	@GetMapping("/login")
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		
		// return "plain-login";

		return "fancy-login";
		
	}
	
//	@PostMapping("/welcome")
//	public String showHome() {
//		System.out.println("?????????????????????? ");
//		return "home";
//	}
	
	@GetMapping("/perform_login")
	public String perform_login() {
		System.out.println("||||||||||||||||||| ");
		return "home";
	}
}
