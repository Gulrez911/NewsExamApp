package com.ctet.controllers;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.ctet.common.CommonUtil;
import com.ctet.common.PropertyConfig;
import com.ctet.data.Company;
import com.ctet.data.Question;
import com.ctet.data.Test;
import com.ctet.data.TestLinkTime;
import com.ctet.data.TestUserData;
import com.ctet.data.User;
import com.ctet.data.UserTestSession;
import com.ctet.repositories.TestLinkTimeRepository;
import com.ctet.repositories.TestRepository;
import com.ctet.repositories.UserTestSessionRepository;
import com.ctet.services.CompanyService;
import com.ctet.services.QuestionService;
import com.ctet.services.TestService;
import com.ctet.services.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	PropertyConfig propertyConfig;

	@Autowired
	QuestionService questionService;

	@Autowired
	TestLinkTimeRepository linkTimeRepository;

	@Autowired
	CompanyService companyService;

	@Autowired
	TestService testService;

	@Autowired
	UserTestSessionRepository testSessionRepository;

	@Autowired
	TestRepository testRepository;

	@RequestMapping(value = { "/newHome" }, method = RequestMethod.GET)
	public ModelAndView newHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("dashboard");
		List<Test> listTest = testRepository.findAll();
		mav.addObject("listTest", listTest);
		return mav;
	}

	@RequestMapping(value = { "/newHome2" }, method = RequestMethod.GET)
	public ModelAndView newHome2(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/view/side2");

		return mav;
	}

	@RequestMapping(value = { "/newHome3" }, method = RequestMethod.GET)
	public ModelAndView newHome3(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/view/google");

		return mav;
	}

	@RequestMapping(value = { "/home22" }, method = RequestMethod.GET)
	public ModelAndView home22(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("home3_copy2");

		return mav;
	}

	@RequestMapping(value = { "/demo" }, method = RequestMethod.GET)
	public ModelAndView demo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("demo");

		return mav;
	}

	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("home");
		ModelAndView mav = new ModelAndView("home2");

		User user = new User();

		mav.addObject("user", user);
		return mav;
	}

//	@RequestMapping(value = { "", "/", "login" }, method = RequestMethod.GET)
//	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	@RequestMapping(value = { "/loginAdmin" }, method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("login_new_2");
		User user = new User();
		user.setEmail("admin@gmail.com");
		user.setPassword("12345");
		user.setCompanyName("MC");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/signoff", method = RequestMethod.GET)
	public ModelAndView signoff(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		// ModelAndView mav = new ModelAndView("index");
		ModelAndView mav = new ModelAndView("login_new_2");
		User user = new User();
		// user.setEmail("system@iiiht.com");
		// user.setPassword("1234");
		// user.setCompanyName("IIHT");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/publicTest", method = RequestMethod.GET)
	public ModelAndView showPublicTest(HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("publicTest3");
		ModelAndView mav = new ModelAndView("publicTest3_html");
//		  ModelAndView mav = new ModelAndView("publicTest_new");
		User user = new User();
		TestUserData testUserData = new TestUserData();
		String testId = request.getParameter("testId");

//		String companyId = request.getParameter("companyId");

//		propertyConfig
		String companyId = propertyConfig.getCompanyName();
//		String inviteSent = request.getParameter("inviteSent");
//		String startTime = request.getParameter("startTime");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		String url = propertyConfig.getBaseUrl() + "publicTest?" + request.getQueryString();
		Boolean dontCheckTimeValidity = false;
		try {
			TestLinkTime linkTime = linkTimeRepository.findUniquetestLink(companyId, url);
			dontCheckTimeValidity = (linkTime == null ? false : linkTime.getDontCheckTimeValidity());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			System.out.println("error " + e1.getMessage());
		}
		Company company = companyService.findByCompanyId(companyId);
		if (company == null) {
			return mav;
		}
		user.setCompanyName(company.getCompanyName());
		user.setCompanyId(company.getCompanyId());
		testUserData.setUser(user);
		Test test = testService.findTestById(Long.parseLong(testId));
		testUserData.setTestId(test.getId() + "");
		testUserData.setTestName(test.getTestName());
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("dontCheckTimeValidity", dontCheckTimeValidity);
		mav.addObject("test", test);
		mav.addObject("testUserData", testUserData);
//		mav.addObject("startTime", startTime);
//		mav.addObject("endTime", endTime);

		return mav;
	}

	@RequestMapping(value = "/publicTestAuthenticate", method = RequestMethod.POST)
	public ModelAndView publicTestAuthenticate(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("testUserData") TestUserData testUserData) {

		testUserData.getUser().setPassword("12345");

		if(testUserData.getUser().getPassword()==null) {
			
		}else {
			
			System.out.println("password.......        " + testUserData.getUser().getPassword());
		}

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
			return showPublicTest(request, response);
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

//		MappingJackson2JsonView view = new MappingJackson2JsonView();
//		Map<Object, String> map = new HashMap<>();
//		map.put("ss", "ssss");
//		ModelAndView mav = new ModelAndView(view);
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

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") User user) {
		ModelAndView mav = null;
		String companyName = propertyConfig.getCompanyName();
		user = userService.authenticate(user.getEmail(), user.getPassword(), companyName);
		if (user == null) {
			mav = new ModelAndView("login_new_2");
			user = new User();
			mav.addObject("user", user);
			mav.addObject("message", "Invalid Credentials ");// later put it as label
			mav.addObject("msgtype", "Failure");
			return mav;
		} else {
//			Page<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId(), 0);

			Page<Question> questions = questionService.findByCompanyId(user.getCompanyId(), 0);

			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("companyId", user.getCompanyId());
			// request.getSession().setAttribute("questions", questions);

			mav = new ModelAndView("lms_question_list");
			mav.addObject("qs", questions.getContent());
			CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), 0, "lmsQuestion_list", null);
		}
		return mav;

	}
}