package com.ctet.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

 import com.ctet.common.PropertyConfig;
import com.ctet.data.Category;
import com.ctet.data.Exam;
import com.ctet.data.Test;
import com.ctet.data.User;
import com.ctet.repositories.CategoryRepository;
import com.ctet.repositories.ExamRepository;
import com.ctet.services.TestService;

@Controller
//@SessionAttributes("usr2")
public class DashboardController {

	@Autowired
	TestService testService;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	PropertyConfig config;

//	@Autowired
//	DashboardController dashboardController;

//	  @RequestMapping(value = "", method = RequestMethod.GET)
//	  public ModelAndView showRoot(HttpServletRequest request, HttpServletResponse response) {
//		 // return showLogin(request, response);
//		  return dashboardController.newHome(request, response);
//	  }

	@RequestMapping(value = "/sucessrazorpay", method = RequestMethod.POST)
	@ResponseBody
	public String sucessrazorpay(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String payment_id) {
		// return showLogin(request, response);
		return payment_id;
	}

	@RequestMapping(value = { "/","home9" }, method = RequestMethod.GET)
//	@RequestMapping(value = { "/", "home" }, method = RequestMethod.GET)
	public ModelAndView newHome3(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "id", required = false) Long id)throws Exception {
		ModelAndView mav = new ModelAndView("home44_4");
		
		User user = (User) request.getSession().getAttribute("usr2");
		System.out.println("????????????  "+user);
		System.out.println("||||||||||||||||||  "+config.getCompanyName());
		User usr = new User();
		User user2 = new User();
//		usr.setCompanyId(config.getCompanyName());
//		usr.setCompanyName(config.getCompanyName());
	

		List<Category> categories = categoryRepository.getListCategory(config.getCompanyName());
		if (id != null) {
			Category category = categoryRepository.getExamsByCategoryId(id, config.getCompanyName());
			mav.addObject("exams", category.getExams());
			mav.addObject("id", category.getId());
		} else {

			List<Category> categorys = categoryRepository.getListCategory(config.getCompanyName());
			for (Category category2 : categorys) {
				mav.addObject("exams", category2.getExams());

				mav.addObject("id", category2.getId());
				break;
			}

		}

//				List<Exam> exams = examRepository.getListExams(user.getCompanyId());
		//
//				mav.addObject("exams", exams);
		mav.addObject("categories", categories);
		String div="";
		if (user != null) {
			String image="";
			if(user.getImageUrl()!=null) {
				image = "<img src="+user.getImageUrl()+">";
				div = "<div class='btn__purple22' id='profile33'>" +image+ "</div>";
			}else {
				
				div = "<div class='btn__purple' id='profile33'>" + user.getFirstName() + "</div>";
			}
		} else {

			div = "<div class='btn__purple' id='trigger'>Sign In</div>";
		}
		mav.addObject("usr4", user);
		mav.addObject("user", div);
		mav.addObject("user2", user2);
		mav.addObject("usr", usr);
		return mav;
	}

	@RequestMapping(value = { "home2" }, method = RequestMethod.GET)
	public ModelAndView newHome2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "id", required = false) Long id) {
//			ModelAndView mav = new ModelAndView("home44");
		ModelAndView mav = new ModelAndView("home44_2");
//			ModelAndView mav = new ModelAndView("home44_3");

		List<Category> categories = categoryRepository.getListCategory(config.getCompanyName());
		if (id != null) {
			Category category = categoryRepository.getExamsByCategoryId(id, config.getCompanyName());
			mav.addObject("exams", category.getExams());
			mav.addObject("id", category.getId());
		} else {

			List<Category> categorys = categoryRepository.getListCategory(config.getCompanyName());
			for (Category category2 : categorys) {
				mav.addObject("exams", category2.getExams());

				mav.addObject("id", category2.getId());
				break;
			}

		}

		User usr = new User();
		usr.setCompanyId(config.getCompanyName());
		usr.setCompanyName(config.getCompanyName());
		mav.addObject("usr", usr);
//			List<Exam> exams = examRepository.getListExams(user.getCompanyId());
		//
//			mav.addObject("exams", exams);
		mav.addObject("categories", categories);
		return mav;
	}

//	@RequestMapping(value = { "/", "home" }, method = RequestMethod.GET)
	@RequestMapping(value = { "home3" }, method = RequestMethod.GET)
	public ModelAndView newHome(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "id", required = false) Long id) {
//		ModelAndView mav = new ModelAndView("home44");
//		ModelAndView mav = new ModelAndView("home44_2");
		ModelAndView mav = new ModelAndView("home44_3");

		List<Category> categories = categoryRepository.getListCategory(config.getCompanyName());
		if (id != null) {
			Category category = categoryRepository.getExamsByCategoryId(id, config.getCompanyName());
			mav.addObject("exams", category.getExams());
			mav.addObject("id", category.getId());
		} else {

			List<Category> categorys = categoryRepository.getListCategory(config.getCompanyName());
			for (Category category2 : categorys) {
				mav.addObject("exams", category2.getExams());

				mav.addObject("id", category2.getId());
				break;
			}

		}

//		List<Exam> exams = examRepository.getListExams(user.getCompanyId());
//
//		mav.addObject("exams", exams);
		mav.addObject("categories", categories);
		return mav;
	}

	@RequestMapping(value = { "/examTest" }, method = RequestMethod.GET)
	public ModelAndView examTest(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "examId", required = false) Long examId) {
//		ModelAndView mav = new ModelAndView("examTests");
		ModelAndView mav = new ModelAndView("examTests2");

		User user = (User) request.getSession().getAttribute("usr2");
		System.out.println("????????????  "+user);
		User usr = new User();
		User user2 = new User();
		List<Category> categories = categoryRepository.getListCategory(config.getCompanyName());
		if (examId != null) {
			Exam exam = examRepository.getTestsByExamId(examId, config.getCompanyName());
//			for(Test test: exam.getTests2()) {
//				
//			}

			mav.addObject("tests", exam.getTests2());
		}

//		List<Exam> exams = examRepository.getListExams(user.getCompanyId());
//
//		mav.addObject("exams", exams);
		mav.addObject("categories", categories);
		
		
		
		
//		User user2 = (User) request.getSession().getAttribute("usr2");
		String div="";
		if (user != null) {
			String image="";
			if(user.getImageUrl()!=null) {
				image = "<img src="+user.getImageUrl()+">";
				div = "<div class='btn__purple22' id='profile33'>" +image+ "</div>";
			}else {
				
				div = "<div class='btn__purple' id='profile33'>" + user.getFirstName() + "</div>";
			}
		} else {

			div = "<div class='btn__purple' id='trigger'>Sign In</div>";
		}
		mav.addObject("usr4", user);
		mav.addObject("user", div);
		mav.addObject("user2", user2);
		mav.addObject("usr", usr);
		return mav;
	}

}