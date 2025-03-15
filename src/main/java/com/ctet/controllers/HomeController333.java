package com.ctet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ctet.entity.Employee;
import com.ctet.repositories.EmpRepository;

@Controller
public class HomeController333 {

	@Autowired
	EmpRepository emprepo;
//	private Logger logger = Logger.getLogger(HomeController.class);

	@GetMapping("/home")
	public String view() {
//		logger.info("View method called");
		System.out.println("testing");
//		TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
//		String exp = "";
//		CronTrigger trigger = new CronTrigger("test",timeZone);
		return "index";
	}

	@GetMapping("/add")
	public ModelAndView veiew() {
		ModelAndView mav = new ModelAndView("add");
		mav.addObject("employee", new Employee());
		return mav;
	}

	@PostMapping("/save")
	public ModelAndView save(@ModelAttribute("employee") Employee employee) throws Exception {
		ModelAndView mav = new ModelAndView("list");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(employee.getPassword());
		employee.setPassword(encodedPassword);
		emprepo.save(employee);

		List<Employee> list = emprepo.findAll();
		mav.addObject("list", list);
		mav.addObject("msg", "Employee saved");
		return mav;
	}
}
