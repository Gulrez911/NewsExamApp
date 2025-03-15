package com.ctet.controllers;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ctet.common.CommonUtil;
import com.ctet.common.EmailGenericMessageThread;
import com.ctet.common.PropertyConfig;
import com.ctet.data.Category;
import com.ctet.data.Question;
import com.ctet.data.Test;
import com.ctet.data.TestUserData;
import com.ctet.data.User;
import com.ctet.data.UserTestSession;
import com.ctet.repositories.CategoryRepository;
import com.ctet.repositories.ExamRepository;
import com.ctet.repositories.UserTestSessionRepository;
import com.ctet.services.TestService;
import com.ctet.services.UserService;

@Controller
//@SessionAttributes("usr2")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	TestService testService;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserTestSessionRepository testSessionRepository;

	@Autowired
	PropertyConfig config;

	private static Logger logger = LogManager.getLogger(UserController.class);
	
	@RequestMapping(value = { "googleSignAuth2" }, method = RequestMethod.POST)
	public ModelAndView googleSignAuth2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "imageUrl", required = false) String imageUrl) throws Exception {
		ModelAndView mav = new ModelAndView("home44_4");

		User usr = new User();
		usr.setCompanyId(config.getCompanyName());
		usr.setCompanyName(config.getCompanyName());
		String userName = "";

		String[] nameArray = name.split(" ");
		String firstName = "";
		String lastName = "";
		int flag = 0;
		for (String w : nameArray) {
			if (flag == 0) {
				firstName = w;
			} else {
				lastName += w + " ";
			}
			flag = 1;
		}
		System.out.println(userName.trim() + "\n");

		usr.setFirstName(firstName);
		usr.setLastName(lastName);
		usr.setEmail(email);
		usr.setPassword("12345");
		usr.setImageUrl(imageUrl);
		userService.saveOrUpdate(usr);
		request.getSession().setAttribute("usr2", usr);
		User user2 = new User();
		usr = new User();
		usr.setCompanyId(config.getCompanyName());
		usr.setCompanyName(config.getCompanyName());
		mav.addObject("usr", usr);

		List<Category> categories = categoryRepository.getListCategory(config.getCompanyName());

		List<Category> categorys = categoryRepository.getListCategory(config.getCompanyName());
		for (Category category2 : categorys) {
			mav.addObject("exams", category2.getExams());

			mav.addObject("id", category2.getId());
			break;
		}

		mav.addObject("categories", categories);

		User usr2 = (User) request.getSession().getAttribute("usr2");
		String div = "";
		if (usr2 != null) {
			String image = "";
			if (usr2.getImageUrl() != null) {
				image = "<img src=" + usr2.getImageUrl() + ">";
				div = "<div class='btn__purple22' id='profile33'>" + image + "</div>";
			} else {

				div = "<div class='btn__purple' id='profile33'>" + usr2.getFirstName() + "</div>";
			}
		} else {

			div = "<div class='btn__purple' id='trigger'>Sign In</div>";
		}
		mav.addObject("usr4", usr2);
		mav.addObject("user", div);
		mav.addObject("user2", user2);
		return mav;
	}

	@GetMapping("/sendOtp")
	@ResponseBody
	public Map<String, Object> sendOtp(HttpServletRequest request, @RequestParam String email) {
		Map<String, Object> map1 = new HashMap<>();
		User user = userService.findByPrimaryKey(email);
		String password = "";
		if (user == null) {
			password = "Your email is not found";
			map1.put("notfound", password);
		} else {
			password = "Here it is : " + user.getPassword();
		}
		try {
			EmailGenericMessageThread thread = new EmailGenericMessageThread(email, "Reset", password, config);
			thread.run();
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Test link mail could not be sent for " + email;
		}
		logger.info("Send Mail called");
		map1.put("usercheck2", user);
		return map1;
	}

	@RequestMapping(value = { "reset" }, method = RequestMethod.GET)
	public ModelAndView reset(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("forget_password");

		User user = (User) request.getSession().getAttribute("usr2");
		System.out.println("????????????  " + user);

		String div = "";
//		if (user != null) {
//			div = "<div class='btn__purple' id='profile33'>" + user.getFirstName() + "</div>";
//		} else {
//
//			div = "<div class='btn__purple' id='trigger'>Sign In</div>";
//		}

		if (user != null) {
			String image = "";
			if (user.getImageUrl() != null) {
				image = "<img src=" + user.getImageUrl() + ">";
				div = "<div class='btn__purple22' id='profile33'>" + image + "</div>";
			} else {

				div = "<div class='btn__purple' id='profile33'>" + user.getFirstName() + "</div>";
			}
		} else {

			div = "<div class='btn__purple' id='trigger'>Sign In</div>";
		}
		mav.addObject("usr4", user);
		mav.addObject("user", div);

		return mav;
	}

	@GetMapping("/sessionCheck")
	@ResponseBody
	public Map<String, Object> sessionCheck(HttpServletRequest request) {
		Map<String, Object> map1 = new HashMap<>();
		User user = (User) request.getSession().getAttribute("usr2");
		map1.put("usercheck", user);
		return map1;
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();

		User usr2 = new User();
//			User usr2 = (User) request.getSession().getAttribute("usr2");
		System.out.println("????????????  " + usr2);
//		return "redirect:home";
		return "redirect:home9";
	}

	@GetMapping("/authCheck")
	@ResponseBody
	public Map<String, Object> authCheck(HttpServletRequest request, @RequestParam String email,
			@RequestParam String password) {
		Map<String, Object> map1 = new HashMap<>();
		String companyName = config.getCompanyName();
		User user = userService.authenticate(email, password, companyName);
		map1.put("user44", user);
		map1.put("user22", "gul");
		return map1;
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ModelAndView auth(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") User user, @RequestParam(name = "id", required = false) Long id) {
		ModelAndView mav = null;
		String companyName = config.getCompanyName();
		user = userService.authenticate(user.getEmail(), user.getPassword(), companyName);
		if (user == null) {
			mav = new ModelAndView("home44_4");
			user = new User();
			mav.addObject("user", user);
			mav.addObject("message", "Invalid Credentials ");// later put it as label
			mav.addObject("msgtype", "Failure");
			return mav;
		} else {
			mav = new ModelAndView("home44_4");
			request.getSession().setAttribute("usr2", user);
//			  user = (User) request.getSession().getAttribute("usr2");
			System.out.println("????????????  " + user);
			User usr = new User();
			User user2 = new User();
//			usr.setCompanyId(config.getCompanyName());
//			usr.setCompanyName(config.getCompanyName());
			mav.addObject("usr", usr);
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
			mav.addObject("categories", categories);
//			String div = "<div class='btn__purple'  id='profile33'>" + user.getFirstName() + "</div>";
			String div = "";
			if (user != null) {
				String image = "";
				if (user.getImageUrl() != null) {
					image = "<img src=" + user.getImageUrl() + ">";
					div = "<div class='btn__purple22' id='profile33'>" + image + "</div>";
				} else {

					div = "<div class='btn__purple' id='profile33'>" + user.getFirstName() + "</div>";
				}
			}
			mav.addObject("usr4", user);

			mav.addObject("user", div);
			mav.addObject("user2", user2);
		}
		return mav;

	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView listUsers(HttpServletResponse response, HttpServletRequest request) throws Exception {
//		User user = (User) request.getSession().getAttribute("user");
//		List<User> users = userService.findByCompany(user.getCompanyId());
		ModelAndView mav = new ModelAndView("add_user");
//		mav.addObject("users", users);
		User usr = new User();
//		usr.setCompanyId(user.getCompanyId());
//		usr.setCompanyName(user.getCompanyName());
		mav.addObject("usr", usr);
		return mav;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("usr") User usr) {
//		User user = (User) request.getSession().getAttribute("user");
//		ModelAndView mav = new ModelAndView("add_user2");
		ModelAndView mav = new ModelAndView("home44_4");

		usr.setCompanyId(config.getCompanyName());
		usr.setCompanyName(config.getCompanyName());
		userService.saveOrUpdate(usr);
		request.getSession().setAttribute("usr2", usr);
		User user2 = new User();
		usr = new User();
		usr.setCompanyId(config.getCompanyName());
		usr.setCompanyName(config.getCompanyName());
		mav.addObject("usr", usr);

		List<Category> categories = categoryRepository.getListCategory(config.getCompanyName());

		List<Category> categorys = categoryRepository.getListCategory(config.getCompanyName());
		for (Category category2 : categorys) {
			mav.addObject("exams", category2.getExams());

			mav.addObject("id", category2.getId());
			break;
		}

		mav.addObject("categories", categories);

		User usr2 = (User) request.getSession().getAttribute("usr2");
		String div = "";
		if (usr2 != null) {
			String image="";
			if(usr2.getImageUrl()!=null) {
				image = "<img src="+usr2.getImageUrl()+">";
				div = "<div class='btn__purple22' id='profile33'>" +image+ "</div>";
			}else {
				
				div = "<div class='btn__purple' id='profile33'>" + usr2.getFirstName() + "</div>";
			}
		} else {

			div = "<div class='btn__purple' id='trigger'>Sign In</div>";
		}
		mav.addObject("usr4", usr2);
		
		
		
		mav.addObject("user", div);
		mav.addObject("user2", user2);
		return mav;
	}

	@RequestMapping(value = "/publicTestAuthenticate3", method = RequestMethod.GET)
	public ModelAndView publicTestAuthenticate3(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "testId", required = false) String testId,
			@RequestParam(name = "testName", required = false) String testName) {

		User user = (User) request.getSession().getAttribute("usr2");
		TestUserData testUserData = new TestUserData();
		testUserData.setUser(user);
		testUserData.setTestId(testId);
		testUserData.setTestName(testName);
		System.out.println("password.......        " + testUserData.getUser().getPassword());
		Test test = testService.findTestById(Long.parseLong(testUserData.getTestId()));

		request.getSession().setAttribute("submitted", null);

		UserTestSession session = testSessionRepository.findByPrimaryKey(testUserData.getUser().getEmail(),
				test.getTestName(), test.getCompanyId());
		String userNameNew = "";
		if (session == null) {
			userNameNew = testUserData.getUser().getEmail();
		} else {
			List<UserTestSession> sessions = testSessionRepository.findByTestNamePart(
					testUserData.getUser().getEmail() + "[" + test.getId(), test.getTestName(), test.getCompanyId());
			int noOfConfAttempts = test.getNoOfConfigurableAttempts() == null ? 50 : test.getNoOfConfigurableAttempts();
			if (noOfConfAttempts <= (sessions.size() + 1)) {
				ModelAndView mav = new ModelAndView("studentNoTest_ExceededAttempts");
				mav.addObject("firstName", testUserData.getUser().getFirstName());
				mav.addObject("lastName", testUserData.getUser().getLastName());
				mav.addObject("attempts", sessions.size() + 1);
				return mav;
			}

			userNameNew = testUserData.getUser().getEmail() + "[" + test.getId() + "-" + (sessions.size() + 1 + "]");
		}

		boolean validate = validateDomainCheck(test, testUserData.getUser().getEmail());
		if (!validate) {
			ModelAndView mav = new ModelAndView("studentNoTest_Domain");
			mav.addObject("firstName", testUserData.getUser().getFirstName());
			mav.addObject("lastName", testUserData.getUser().getLastName());
			mav.addObject("domain", test.getDomainEmailSupported());
			return mav;
		}
		testUserData.getUser().setEmail(userNameNew);
		userService.saveOrUpdate(testUserData.getUser());
		request.getSession().setAttribute("user", testUserData.getUser());

		request.getSession().setAttribute("test", test);
		if (testUserData.getUser() == null) {
//			return showPublicTest(request, response);
		}
		String userId = URLEncoder
				.encode(Base64.getEncoder().encodeToString(testUserData.getUser().getEmail().getBytes()));
		String companyId = URLEncoder.encode(test.getCompanyId());
		String inviteSent = (String) request.getSession().getAttribute("inviteSent");
		String append = "";
		if (inviteSent != null) {
			append += "&inviteSent=" + inviteSent;
		}
		// String url =
		// "redirect:/startTestSession2?userId="+userId+"&companyId="+companyId+"&testId="+test.getId()+append+"&sharedDirect=yes&startDate="+URLEncoder.encode(Base64.getEncoder().encodeToString(testUserData.getStartTime().getBytes()))+"&endDate="+URLEncoder.encode(Base64.getEncoder().encodeToString(testUserData.getEndTime().getBytes()));
		String url = "redirect:/startTestSession?userId=" + userId + "&companyId=" + companyId + "&testId="
				+ test.getId();

		ModelAndView mav = new ModelAndView(url);

		return mav;
	}

	private boolean validateDomainCheck(Test test, String email) {
		if (test.getDomainEmailSupported() == null || test.getDomainEmailSupported().trim().length() == 0
				|| test.getDomainEmailSupported().equals("*")) {
			return true;
		}

		String dom = email.substring(email.indexOf("@") + 1, email.length());
		if (test.getDomainEmailSupported().contains(dom)) {
			return true;
		}

		return false;
	}

//	#6B46C1

//	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
//	public ModelAndView listUsers(HttpServletResponse response, HttpServletRequest request) throws Exception {
//		User user = (User) request.getSession().getAttribute("user");
//		List<User> users = userService.findByCompany(user.getCompanyId());
//		ModelAndView mav = new ModelAndView("add_user");
//		mav.addObject("users", users);
//		User usr = new User();
//		usr.setCompanyId(user.getCompanyId());
//		usr.setCompanyName(user.getCompanyName());
//		mav.addObject("usr", usr);
//		return mav;
//	}
}
