package com.ctet.controllers;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ctet.common.QuestionSequence;
import com.ctet.common.SectionSequence;
import com.ctet.data.Question;
import com.ctet.data.QuestionMapper;
import com.ctet.data.QuestionMapperInstance;
import com.ctet.data.QuestionType;
import com.ctet.data.Section;
import com.ctet.data.SectionInstance;
import com.ctet.data.Test;
import com.ctet.data.User;
import com.ctet.data.UserTestSession;
import com.ctet.data.UserTestTimeCounter;
import com.ctet.repositories.QuestionMapperRepository;
import com.ctet.repositories.UserRepository;
import com.ctet.repositories.UserTestSessionRepository;
import com.ctet.repositories.UserTestSessionService;
import com.ctet.services.QuestionMapperInstanceService;
import com.ctet.services.QuestionMapperService;
import com.ctet.services.SectionInstanceService;
import com.ctet.services.SectionService;
import com.ctet.services.TestService;
import com.ctet.services.UserService;
import com.ctet.services.UserTestTimeCounterService;
import com.ctet.web.dto.QuestionInstanceDto;
import com.ctet.web.dto.SectionInstanceDto;
import com.ctet.web.forms.StudentTestForm;

@Controller
public class StudentController {

	@Autowired
	UserTestTimeCounterService counterService;

	@Autowired
	UserService userService;

	@Autowired
	TestService testService;

	@Autowired
	UserTestSessionRepository testSessionRepository;

	@Autowired
	UserTestSessionService userTestSessionService;

	@Autowired
	SectionService sectionService;

	@Autowired
	QuestionMapperService questionMapperService;

	@Autowired
	QuestionMapperInstanceService questionMapperInstaceService;

	@Autowired
	SectionInstanceService sectionInstanceService;

	@Autowired
	QuestionMapperRepository questionMapperRep;

	@Autowired
	UserRepository userRepository ;
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);

	private void setTimeInCounter(HttpServletRequest request, Long timeElapsed) {
		StudentTestForm studentTest = (StudentTestForm) request.getSession().getAttribute("studentTestForm");
		studentTest.setTotalTestTimeElapsedInSeconds(studentTest.getTotalTestTimeElapsedInSeconds() + timeElapsed);
	}

	private void putMiscellaneousInfoInModel(ModelAndView model, HttpServletRequest request) {
		StudentTestForm studentTest = (StudentTestForm) request.getSession().getAttribute("studentTestForm");
		model.addObject("studentTestForm", studentTest);

		Test test = (Test) request.getSession().getAttribute("test");
		UserTestTimeCounter counter = counterService.findByPrimaryKey(test.getId(), studentTest.getEmailId(),
				studentTest.getCompanyId());
		if (counter == null) {
			model.addObject("timeCounter", new Long(0));
		} else {
			model.addObject("timeCounter", counter.getTimeCounter());
		}
	}

	@RequestMapping(value = "/timecounterUpdate", method = RequestMethod.POST)
	public @ResponseBody String timecounterUpdate(@RequestParam Long timecounter, @RequestParam Long testId,
			@RequestParam String companyId, @RequestParam String email, HttpServletRequest request,
			HttpServletResponse response) {
		UserTestTimeCounter counter = new UserTestTimeCounter();
		counter.setCompanyId(companyId);
		counter.setEmail(email);
		counter.setTestId(testId);
		counter.setTimeCounter(timecounter);

		Test test = (Test) request.getSession().getAttribute("test");
		counter.setTestName(test.getTestName());
		counter.setCompanyName(test.getCompanyName());
		counterService.saveOrUpdate(counter);
		return "ok";
	}

	private String decodeUserId(String encodedUri) {
		// Decode data on other side, by processing encoded data
		String decoded = new String(DatatypeConverter.parseBase64Binary(encodedUri));
		// System.out.println("user id is " + decoded);
		return decoded;
	}

	public String getUserAfterCheckingNoOfAttempts(String user, String companyId, Test test,
			HttpServletRequest request) {
		UserTestSession session = testSessionRepository.findByPrimaryKey(user, test.getTestName(), test.getCompanyId());
		String userNameNew = "";
		if (session == null) {
			userNameNew = user;
			request.getSession().setAttribute("noOfAttempts", 0);
			return userNameNew;
		} else {
			/**
			 * Step 2 - find out how many sessions for the given test the user has taken
			 */
			List<UserTestSession> sessions = testSessionRepository.findByTestNamePart(user + "[" + test.getId(),
					test.getTestName(), test.getCompanyId());
			// int noOfConfAttempts = test.getNoOfConfigurableAttempts()
			// ==null?50:test.getNoOfConfigurableAttempts();
			/**
			 * Check whether test is configured with a domain having configurable no of
			 * attempts
			 */
			String domain = user.substring(user.indexOf("@") + 1, user.length());
			int noOfConfAttempts = getNoOfConfigurableAttempts(test, domain);
			/**
			 * End above check
			 */
			if (noOfConfAttempts <= (sessions.size() + 1)) {
				return "fail";
			}

			userNameNew = user + "[" + test.getId() + "-" + (sessions.size() + 1 + "]");
			request.getSession().setAttribute("noOfAttempts", (sessions.size() + 1));
			return userNameNew;
		}
	}

	private Integer getNoOfConfigurableAttempts(Test test, String domain) {
		int noOfConfAttempts = test.getNoOfConfigurableAttempts() == null ? 50 : test.getNoOfConfigurableAttempts();
		return noOfConfAttempts;
	}

	@RequestMapping(value = "/startTestSession", method = RequestMethod.GET)
	public ModelAndView studentHome2(@RequestParam String userId, @RequestParam String companyId,
			@RequestParam String testId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav;

		request.getSession().setAttribute("dontCheckTimeValidity", null);

		StudentTestForm studentTest = new StudentTestForm();
		userId = decodeUserId((String) request.getParameter("userId"));
		companyId = (String) request.getParameter("companyId");
		// ModelAndView model= new ModelAndView("intro");
		ModelAndView model = new ModelAndView("intro2");
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
		String time = localDateFormat.format(new Date());
		studentTest.setCurrentTime(time);
		User userDetails = userService.findByPrimaryKey(userId, companyId);
		request.getSession().setAttribute("user", userDetails);

		if (userDetails != null) {
			Test testDetails = testService.findTestById(Long.parseLong(testId), companyId);
			/**
			 * Get no of attempts for the email id and make configurabled attempts work for
			 * test givers
			 */
			String email = "";
			if (userDetails.getEmail().lastIndexOf("[") > 0) {
				email = userDetails.getEmail().substring(0, userDetails.getEmail().lastIndexOf("["));
			} else {
				email = userDetails.getEmail();
			}
			UserTestSession session2 = testSessionRepository.findByPrimaryKey(email, testDetails.getTestName(),
					testDetails.getCompanyId());
			if (session2 != null) {
				email = getUserAfterCheckingNoOfAttempts(email, testDetails.getCompanyId(), testDetails, request);
				if (email.equals("fail")) {
					mav = new ModelAndView("studentNoTest_ExceededAttempts");
					mav.addObject("firstName", userDetails.getFirstName());
					mav.addObject("lastName", userDetails.getLastName());
					mav.addObject("attempts", testDetails.getNoOfConfigurableAttempts() == null ? 50
							: testDetails.getNoOfConfigurableAttempts());
					return mav;
				} else {
					userDetails.setEmail(email);
					userDetails.setId(null);
					userService.saveOrUpdate(userDetails);
					request.getSession().setAttribute("user", userDetails);
				}
			}
			userDetails.setEmail(email);
			/**
			 * End code put to check configurabled attempts work for test givers who are
			 * send private test links through email
			 */

			studentTest.setUserName(userDetails.getFirstName() + " " + userDetails.getLastName());
			studentTest.setEmailId(userDetails.getEmail());
			testId = (String) request.getParameter("testId");

			/**
			 * Add code for image logo mapping
			 */
			if (testDetails.getTestType() == null) {
				studentTest.setTechLogo("https://yaksha.online/images/Java.png");
			}

			if (testDetails.getTestType().equals("Java")) {
				studentTest.setTechLogo("https://yaksha.online/images/Java.png");
			} else if (testDetails.getTestType().equals("Microsoft technologies")) {
				studentTest.setTechLogo("https://yaksha.online/images/Microsoft.png");
			} else if (testDetails.getTestType().equals("C/C++")) {
				studentTest.setTechLogo("https://yaksha.online/images/C.png");
			} else if (testDetails.getTestType().equals("Python")) {
				studentTest.setTechLogo("https://yaksha.online/images/Python.png");
			} else if (testDetails.getTestType().equals("Python")) {
				studentTest.setTechLogo("https://yaksha.online/images/C.png");
			} else if (testDetails.getTestType().equals("General Knowledge")) {
				studentTest.setTechLogo("https://yaksha.online/images/GK.png");
			} else if (testDetails.getTestType().equals("Composite Test")) {
				studentTest.setTechLogo("https://yaksha.online/images/All_In_1.png");
			} else {
				studentTest.setTechLogo("https://yaksha.online/images/All_In_1.png");
			}
			//

			/**
			 * End code for image logo mapping
			 */

			User createTestUser = userService.findByPrimaryKey(testDetails.getCreatedBy(), companyId);
			studentTest.setTestCreatorName(createTestUser.getFirstName() + " " + createTestUser.getLastName());
			request.getSession().setAttribute("test", testDetails);
			// List<Section> sections =
			// sectionService.getSectionsForTest(testDetails.getTestName(),companyId);
			studentTest.setTestCreatedBy(testDetails.getCreatedBy());

			if (testDetails.getTotalMarks() == null) {
				model = new ModelAndView("studentMessageTest_WithNoQs");
				model.addObject("studentTestForm", studentTest);
				return model;
			}
			int questionsCountInAllSections = testDetails.getTotalMarks();
			int allQuestionsTimeInMin = 0;

			if (testDetails.getTestTimeInMinutes() == null || testDetails.getTestTimeInMinutes() == 0) {
				allQuestionsTimeInMin = 45;
			} else {
				allQuestionsTimeInMin = testDetails.getTestTimeInMinutes();
			}

			studentTest.setCompanyId(companyId);
			studentTest.setEmailId(userDetails.getEmail());
			studentTest.setTestName(testDetails.getTestName());
			studentTest.setTotalQuestions(questionsCountInAllSections);
			studentTest.setDuration(allQuestionsTimeInMin);
			studentTest.setPublishedDate(testDetails.getCreateDate());
			studentTest.setFirstName(userDetails.getFirstName());
			studentTest.setLastName(userDetails.getLastName());
			studentTest.setTestCreatedBy(testDetails.getCreatedBy());
			String pattern = "dd-MM-yyyy HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			studentTest.setTestCreationDate(simpleDateFormat.format(testDetails.getCreateDate()));
			// Integer noOfAttempts =
			// userTestSessionService.findNoOfAttempsByUserForTest(userDetails.getEmail(),
			// testDetails.getTestName(), userDetails.getCompanyId());
			// studentTest.setNoOfAttempts(noOfAttempts == null || noOfAttempts == 0
			// ?1:noOfAttempts +1);
			UserTestSession session = userTestSessionService.findUserTestSession(userDetails.getEmail(),
					testDetails.getTestName(), userDetails.getCompanyId());
			if (session != null && session.getComplete()) {

				studentTest.setLastUpdated(simpleDateFormat
						.format(session.getUpdateDate() == null ? session.getCreateDate() : session.getUpdateDate()));
				studentTest.setNoOfAttempts(session.getNoOfAttempts());
				model = new ModelAndView("studentNoTest");
				model.addObject("studentTestForm", studentTest);
				return model;
			} else if (session != null && !session.getComplete()) {
				studentTest.setNoOfAttempts(session.getNoOfAttempts());
			} else if (session == null) {
				studentTest.setNoOfAttempts(1);
				if (!userDetails.getEmail().contains("[")) {
					request.getSession().setAttribute("noOfAttempts", 0);
				}

			}
			studentTest.setNoOfAttemptsAvailable(
					testDetails.getNoOfConfigurableAttempts() == null ? 50 : testDetails.getNoOfConfigurableAttempts());
		}

		model.addObject("studentTestForm", studentTest);
		request.getSession().setAttribute("studentTestForm", studentTest);
		model.addObject("userName", userDetails.getFirstName() + " " + userDetails.getLastName());
		putMiscellaneousInfoInModel(model, request);
		return model;
	}

	@RequestMapping(value = "/studentJourney", method = RequestMethod.POST)
	public ModelAndView studentStartExam(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("studentTestForm") StudentTestForm studentForm) throws Exception {
		// ModelAndView model= new ModelAndView("test_cognizant");
		System.out.println("new changes");
		ModelAndView model;
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		if (test.getFullStackTest() != null && test.getFullStackTest()) {
			// model= new ModelAndView("test_fstk");
			model = new ModelAndView("test_fstk_new");
		} else {
			model = new ModelAndView("test_cognizant_html");
			
//			model = new ModelAndView("test_cognizant");
//		 		model = new ModelAndView("test_new");
		}
		request.getSession().setAttribute("testStartDate", new Date());
		List<Section> sections = sectionService.getSectionsForTest(test.getTestName(), test.getCompanyId());

		int count = 0;
		List<SectionInstanceDto> sectionInstanceDtos = new ArrayList<>();
		int totalQuestions = test.getTotalMarks();
		for (Section section : sections) {
			// from the sections creating an instance of section mapping with test
			SectionInstanceDto sectionInstanceDto = new SectionInstanceDto();
			sectionInstanceDtos.add(sectionInstanceDto);
			// sectionInstanceDto.setCurrent(current);
			if (count == 0) {
				sectionInstanceDto.setCurrent(true);

				List<QuestionMapper> questionMappers = questionMapperService.getQuestionsForSection(test.getTestName(),
						section.getSectionName(), user.getCompanyId());
				// Collections.shuffle(questionMappers);
				List<QuestionMapper> questionMappersActual = questionMappers.subList(0,
						section.getNoOfQuestionsToBeAsked());
				Collections.shuffle(questionMappersActual);
				List<QuestionInstanceDto> questionMapperInstances = new ArrayList<QuestionInstanceDto>();
				int pos = 0;
				for (QuestionMapper questionMapper : questionMappersActual) {
					// creating the instances of question mapper instance entity
					QuestionInstanceDto questionInstanceDto = new QuestionInstanceDto();
					pos++;
					questionInstanceDto.setPosition(pos);
					QuestionMapperInstance questionMapperInstance = null;
					if (section.getPercentQuestionsAsked() == 100) {
						questionMapperInstance = questionMapperInstaceService.removeDublicateAndGetInstance(
								questionMapper.getQuestion().getQuestionText(), test.getTestName(),
								section.getSectionName(), user.getEmail(), user.getCompanyId());

					} else {
						questionMapperInstance = questionMapperInstaceService.removeDublicateAndGetInstance(
								questionMapper.getQuestion().getQuestionText(), test.getTestName(),
								section.getSectionName(), user.getEmail(), user.getCompanyId());
					}

					if (questionMapperInstance == null) {
						questionMapperInstance = new QuestionMapperInstance();
						questionMapperInstance.setQuestionMapper(questionMapper);
					}

					questionInstanceDto.setQuestionMapperInstance(questionMapperInstance);

					questionMapperInstance.setQuestionMapper(questionMapper);
					questionMapperInstances.add(questionInstanceDto);

				}
				sectionInstanceDto.setFirst(true);
				sectionInstanceDto.setQuestionInstanceDtos(questionMapperInstances);

				/**
				 * For only 1 Q and 1 section..adding this
				 */
				if (sections.size() == 1) {
					if (questionMappersActual.size() == 1) {
						sectionInstanceDto.setLast(true);
					}
				}

				/**
				 * End For only 1 Q and 1 section..adding this
				 */

				model.addObject("currentSection", sectionInstanceDto);

				model.addObject("currentQuestion", questionMapperInstances.get(0)); // problem area
				request.getSession().setAttribute("currentSection", sectionInstanceDto);
				/**
				 * Get the fullstack for Q if type is full stack.
				 * 
				 */

				/**
				 * End full stack check
				 */
			}
			sectionInstanceDto.setNoOfQuestions(section.getNoOfQuestionsToBeAsked());
			sectionInstanceDto.setSection(section);
			count++;
			// fetch the questions based on the associated sections

			if (sectionInstanceDto.getQuestionInstanceDtos() != null
					&& sectionInstanceDto.getQuestionInstanceDtos().size() == 0) {
				populateWithQuestions(sectionInstanceDto, test.getTestName(),
						sectionInstanceDto.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
			}
		}

		request.getSession().setAttribute("sectionInstanceDtos", sectionInstanceDtos);
		putMiscellaneousInfoInModel(model, request);
		processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
		model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		// model.addObject("percentage", "0");
		// model.addObject("totalQuestions", ""+totalQuestions);
		// model.addObject("noAnswered", "0");
		model.addObject("confidenceFlag", test.getConsiderConfidence());
		/**
		 * Add the time counter part - retrieved time counter for sessions that were
		 * disrupted.
		 */
		UserTestTimeCounter counter = counterService.findByPrimaryKey(test.getId(), user.getEmail(),
				user.getCompanyId());
		if (counter == null) {
			model.addObject("timeCounter", new Long(0));
		} else {
			model.addObject("timeCounter", counter.getTimeCounter());
		}
		model.addObject("firstpage", "yes");
		return model;
	}

	@RequestMapping(value = "/changeSection", method = RequestMethod.GET)
	public ModelAndView changeSection(@RequestParam String sectionName, @RequestParam String timeCounter,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("studentTestForm") StudentTestForm studentForm) {
		// ModelAndView model= new ModelAndView("test_cognizant");
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		ModelAndView model;
		if (test.getFullStackTest() != null && test.getFullStackTest()) {
			// model= new ModelAndView("test_fstk");
			model = new ModelAndView("test_fstk_new");
		} else {
			model = new ModelAndView("test_cognizant");
//		 		model= new ModelAndView("test_new");
		}

		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");

		int count = 0;
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			count++;
			sectionInstanceDto.setCurrent(false);
			// sectionInstanceDto.getQuestionInstanceDtos().clear();
			if (sectionInstanceDto.getSection().getSectionName().equals(sectionName)) {
				if (count == 1) {
					sectionInstanceDto.setFirst(true);
					sectionInstanceDto.setLast(false);
				}

				if (count == sectionInstanceDtos.size()) {
					sectionInstanceDto.setFirst(false);
					sectionInstanceDto.setLast(false);
				}

				if (count == 1 && sectionInstanceDtos.size() == 1) {
					sectionInstanceDto.setFirst(true);
					sectionInstanceDto.setLast(true);
				}
				sectionInstanceDto.setCurrent(true);
				sectionInstanceDto = populateWithQuestions(sectionInstanceDto, test.getTestName(),
						sectionInstanceDto.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				model.addObject("currentSection", sectionInstanceDto);
				QuestionInstanceDto currentQuestion = sectionInstanceDto.getQuestionInstanceDtos().get(0);
				// if(sectionInstanceDto.getQuestionInstanceDtos().size() == 1){
				if (sectionInstanceDto.getQuestionInstanceDtos().size() == 1 && count == sectionInstanceDtos.size()) {
					sectionInstanceDto.setLast(true);
				} else {
					sectionInstanceDto.setLast(false);
				}

				model.addObject("currentQuestion", sectionInstanceDto.getQuestionInstanceDtos().get(0));
				request.getSession().setAttribute("currentSection", sectionInstanceDto);
			}
		}

		request.getSession().setAttribute("sectionInstanceDtos", sectionInstanceDtos);
		model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		putMiscellaneousInfoInModel(model, request);
		setTimeInCounter(request, Long.valueOf(timeCounter));
		processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
		return model;
	}

	@RequestMapping(value = "/nextQuestion", method = RequestMethod.POST)
	public ModelAndView nextQuestion(
			@RequestParam(name = "imageVideoData", required = false) MultipartFile imageVideoData,
			@RequestParam String questionId, @RequestParam String timeCounter, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("currentQuestion") QuestionInstanceDto currentQuestion)
			throws Exception {
		// ModelAndView model= new ModelAndView("test_cognizant");
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		ModelAndView model;
		if (test.getFullStackTest() != null && test.getFullStackTest()) {
			// model= new ModelAndView("test_fstk");
			model = new ModelAndView("test_fstk_new");
		} else {
			model = new ModelAndView("test_cognizant_html");
			
//			model = new ModelAndView("test_cognizant");
//		 		model= new ModelAndView("test_new");
		}

		if (imageVideoData != null && imageVideoData.getSize() != 0) {
			String baseFolder = "";

			Long questionMapperId = currentQuestion.getQuestionMapperId();
			QuestionMapper mapper = questionMapperRep.findById(questionMapperId).get();

			baseFolder += File.separator + user.getEmail() + File.separator + "test" + test.getId() + "qid"
					+ mapper.getQuestion().getId();
			// +File.separator+imageVideoData.getName()
			System.out.println("Uploading file on fileserver start");
			logger.info("Uploading file on fileserver start");
			File file = new File(baseFolder);
			file.mkdirs();
			File actual = new File(baseFolder + File.separator + imageVideoData.getOriginalFilename());
			FileUtils.copyInputStreamToFile(imageVideoData.getInputStream(), actual);
			System.out.println("Uploading file on fileserver start end");
			logger.info("Uploading file on fileserver start end");

			// FileUtils.
		}

		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");
		model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		SectionInstanceDto currentSection = (SectionInstanceDto) request.getSession().getAttribute("currentSection");
		/**
		 * Trying to solve john doe prob
		 */
		currentSection = getCurrentSection(Long.valueOf(questionId), sectionInstanceDtos, currentSection);
		if (currentSection.getQuestionInstanceDtos().size() == 0) {
			populateWithQuestions(currentSection, test.getTestName(), currentSection.getSection().getSectionName(),
					user.getCompanyId(), user.getEmail());
		}
		request.getSession().setAttribute("currentSection", currentSection);
		/**
		 * End john doe prob
		 */

		// just in case a Q is of coding type value that comes from jsp has \r
		// characters.so removng them so they can be rendered next time
		if (currentQuestion.getCode() != null) {
			currentQuestion.setCode(currentQuestion.getCode().replaceAll("\r", ""));
			String rep = "\\\\n";
			String rept = "\\\\t";
			currentQuestion.setCode(currentQuestion.getCode().replaceAll("\n", rep));
			currentQuestion.setCode(currentQuestion.getCode().replaceAll("\t", rept));
		}

		setAnswers(request, currentSection, currentQuestion, questionId, false);
		// setValuesInSession(currentSection, sectionInstanceDtos);

		QuestionSequence questionSequence = new QuestionSequence(currentSection.getQuestionInstanceDtos());
		SectionSequence sectionSequence = new SectionSequence(sectionInstanceDtos);

		currentQuestion = questionSequence.nextQuestion(Long.valueOf(questionId));

		if (currentQuestion == null) {

			SectionInstanceDto nextSection = sectionSequence.nextSection(currentSection.getSection().getSectionName());

			if (nextSection != null) {
				synchronized (this) {
					nextSection = populateWithQuestions(nextSection, test.getTestName(),
							nextSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				}
				// currentSection.getQuestionInstanceDtos().clear();
				currentQuestion = nextSection.getQuestionInstanceDtos().get(0);

				/**
				 * Making sure next and prev button behave for the first and last event
				 */
				questionSequence = new QuestionSequence(nextSection.getQuestionInstanceDtos());
				if (isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
					nextSection.setLast(true);
				} else {
					nextSection.setLast(false);
				}

				if (isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
					nextSection.setFirst(true);
				} else {
					nextSection.setFirst(false);
				}

				model.addObject("currentSection", nextSection);
				nextSection.setCurrent(true);
				currentSection.setCurrent(false);
				model.addObject("currentQuestion", currentQuestion);
				model.addObject("confidenceFlag", test.getConsiderConfidence());
				request.getSession().setAttribute("currentSection", nextSection);
				putMiscellaneousInfoInModel(model, request);
				processPercentages(model, sectionInstanceDtos, test.getTotalMarks());

				return model;
			} else {
				// Save test and generate result
				// questionSequence.nextQuestion(Long.valueOf(questionId));
				System.out.println("user " + user.getEmail() + " testname " + test.getTestName()
						+ " current section name  " + currentSection.getSection().getSectionName() + " ques id "
						+ questionId + " currentSection.getQuestionInstanceDtos().size "
						+ currentSection.getQuestionInstanceDtos().size());
				questionSequence.scan(Long.valueOf(questionId));

				model = new ModelAndView("report");
				putMiscellaneousInfoInModel(model, request);

				return model;
			}
		} else {
			if (isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
				currentSection.setLast(true);
			} else {
				currentSection.setLast(false);
			}

			if (isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
				currentSection.setFirst(true);
			} else {
				currentSection.setFirst(false);
			}
//			 if(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() != null && currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
//			 		currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode().replaceAll("\r", ""));
//			 	}
			model.addObject("currentSection", currentSection);

			model.addObject("currentQuestion", currentQuestion);
			request.getSession().setAttribute("currentSection", currentSection);
			putMiscellaneousInfoInModel(model, request);
			setTimeInCounter(request, Long.valueOf(timeCounter));
			processPercentages(model, sectionInstanceDtos, test.getTotalMarks());

			model.addObject("confidenceFlag", test.getConsiderConfidence());
			return model;
		}

	}

	@RequestMapping(value = "/prevQuestion", method = RequestMethod.POST)
	public ModelAndView prevQuestion(
			@RequestParam(name = "imageVideoData", required = false) MultipartFile imageVideoData,
			@RequestParam String questionId, @RequestParam String timeCounter, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("currentQuestion") QuestionInstanceDto currentQuestion)
			throws Exception {
		// ModelAndView model= new ModelAndView("test_cognizant");
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		ModelAndView model;
		if (test.getFullStackTest() != null && test.getFullStackTest()) {
			// model= new ModelAndView("test_fstk");
			model = new ModelAndView("test_fstk_new");
		} else {
			model = new ModelAndView("test_cognizant_html");
			
//			model = new ModelAndView("test_cognizant");
//		 		model= new ModelAndView("test_new");
		}

		if (imageVideoData != null && imageVideoData.getSize() != 0) {
			String baseFolder = "";

			Long questionMapperId = currentQuestion.getQuestionMapperId();
			QuestionMapper mapper = questionMapperRep.findById(questionMapperId).get();

			baseFolder += File.separator + user.getEmail() + File.separator + "test" + test.getId() + "qid"
					+ mapper.getQuestion().getId();
			// +File.separator+imageVideoData.getName()
			File file = new File(baseFolder);
			file.mkdirs();
			File actual = new File(baseFolder + File.separator + imageVideoData.getOriginalFilename());
			FileUtils.copyInputStreamToFile(imageVideoData.getInputStream(), actual);

		}

		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");
		model.addObject("sectionInstanceDtos", sectionInstanceDtos);

		SectionInstanceDto currentSection = (SectionInstanceDto) request.getSession().getAttribute("currentSection");
		// just in case a Q is of coding type value that comes from jsp has \r
		// characters.so removng them so they can be rendered next time
		if (currentQuestion.getCode() != null) {
			currentQuestion.setCode(currentQuestion.getCode().replaceAll("\r", ""));
			String rep = "\\\\n";
			String rept = "\\\\t";
			currentQuestion.setCode(currentQuestion.getCode().replaceAll("\n", rep));
			currentQuestion.setCode(currentQuestion.getCode().replaceAll("\t", rept));
		}
		setAnswers(request, currentSection, currentQuestion, questionId, false);
		// setValuesInSession(currentSection, sectionInstanceDtos);

		QuestionSequence questionSequence = new QuestionSequence(currentSection.getQuestionInstanceDtos());
		SectionSequence sectionSequence = new SectionSequence(sectionInstanceDtos);
		currentQuestion = questionSequence.previousQuestion(Long.valueOf(questionId));
		if (currentQuestion == null) {

			SectionInstanceDto previousSection = sectionSequence
					.prevSection(currentSection.getSection().getSectionName());

			if (previousSection != null) {
				previousSection = populateWithQuestions(previousSection, test.getTestName(),
						previousSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				// currentSection.getQuestionInstanceDtos().clear();
				currentQuestion = previousSection.getQuestionInstanceDtos()
						.get(previousSection.getQuestionInstanceDtos().size() - 1);
				model.addObject("currentSection", previousSection);
				previousSection.setCurrent(true);
				currentSection.setCurrent(false);
				/**
				 * Making sure next and prev button behave for the first and last event
				 */
				questionSequence = new QuestionSequence(previousSection.getQuestionInstanceDtos());// now get the last
																									// question from the
																									// prev section
				if (isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
					previousSection.setLast(true);
				} else {
					previousSection.setLast(false);
				}

				if (isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
					previousSection.setFirst(true);
				} else {
					previousSection.setFirst(false);
				}

				model.addObject("currentQuestion", currentQuestion);
				request.getSession().setAttribute("currentSection", previousSection);
				putMiscellaneousInfoInModel(model, request);
				processPercentages(model, sectionInstanceDtos, test.getTotalMarks());

				model.addObject("confidenceFlag", test.getConsiderConfidence());
				return model;
			} else {
				// Save test and generate result
				// model= new ModelAndView("intro");
				model = new ModelAndView("intro_new");
				putMiscellaneousInfoInModel(model, request);
				return model;
			}
		} else {
			model.addObject("currentSection", currentSection);

			model.addObject("currentQuestion", currentQuestion);
			if (isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
				currentSection.setLast(true);
			} else {
				currentSection.setLast(false);
			}

			if (isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
				currentSection.setFirst(true);
			} else {
				currentSection.setFirst(false);
			}

			request.getSession().setAttribute("currentSection", currentSection);
			putMiscellaneousInfoInModel(model, request);
			setTimeInCounter(request, Long.valueOf(timeCounter));
			processPercentages(model, sectionInstanceDtos, test.getTotalMarks());

			model.addObject("confidenceFlag", test.getConsiderConfidence());
			return model;
		}

	}

	@RequestMapping(value = "/submitTest", method = RequestMethod.POST)
	public ModelAndView submitTest(
			@RequestParam(name = "imageVideoData", required = false) MultipartFile imageVideoData,
			@RequestParam String questionId, @RequestParam String timeCounter, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("currentQuestion") QuestionInstanceDto currentQuestion)
			throws IOException {
		
		ModelAndView model  = new ModelAndView("test_cognizant_html");
//		userRepository.sql_mode();
//		model = new ModelAndView("test_cognizant");
//		 ModelAndView model= new ModelAndView("test_new");
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");
		model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		SectionInstanceDto currentSection = (SectionInstanceDto) request.getSession().getAttribute("currentSection");

		if (imageVideoData != null && imageVideoData.getSize() != 0) {
			String baseFolder = "";

			Long questionMapperId = currentQuestion.getQuestionMapperId();
			QuestionMapper mapper = questionMapperRep.findById(questionMapperId).get();

			baseFolder += File.separator + user.getEmail() + File.separator + "test" + test.getId() + "qid"
					+ mapper.getQuestion().getId();
			// +File.separator+imageVideoData.getName()
			File file = new File(baseFolder);
			file.mkdirs();
			File actual = new File(baseFolder + File.separator + imageVideoData.getOriginalFilename());
			FileUtils.copyInputStreamToFile(imageVideoData.getInputStream(), actual);

		}
		String confidencePercent = "NA";
		float totQs = 0;
		float totConfidence = 0;
		setAnswers(request, currentSection, currentQuestion, questionId, true);
		// currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getRightChoices().split(",").length
		Boolean codingAssignments = false;
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			saveSection(sectionInstanceDto, request);
			if (test.getConsiderConfidence() != null && test.getConsiderConfidence()) {
				totQs += sectionInstanceDto.getNoOfQuestions();
				for (QuestionInstanceDto dto : sectionInstanceDto.getQuestionInstanceDtos()) {
					if (dto.getConfidence() != null && dto.getConfidence()) {
						totConfidence += 1;
					}

				}

			}
		}

		DecimalFormat df = new DecimalFormat("##.##");
		if (test.getConsiderConfidence() != null && test.getConsiderConfidence()) {
			confidencePercent = df.format(100 * ((float) totConfidence / totQs));
		}

		UserTestSession userTestSession = new UserTestSession();
		userTestSession.setCompanyId(user.getCompanyId());
		userTestSession.setCompanyName(user.getCompanyName());

		userTestSession.setUser(user.getEmail());
		userTestSession.setTest(test);
		userTestSession.setTestName(test.getTestName());
		userTestSession.setComplete(true);
		userTestSession.setFirstName(user.getFirstName());
		userTestSession.setLastName(user.getLastName());

		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			for (QuestionInstanceDto dto : sectionInstanceDto.getQuestionInstanceDtos()) {
				String tp = dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType()
						.getType();
			}
		}

		String sectionsQuestionsNotAnswered = "";
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			userTestSession.setSectionResults(
					(userTestSession.getSectionResults() == null ? "" : userTestSession.getSectionResults()) + ", "
							+ sectionInstanceDto.getSection().getSectionName() + "-"
							+ df.format((new Float(sectionInstanceDto.getTotalCorrectAnswers())
									/ new Float(sectionInstanceDto.getNoOfQuestions())) * 100));
			userTestSession.setSectionsNoOfQuestionsNotAnswered(
					(userTestSession.getSectionsNoOfQuestionsNotAnswered() == null ? ""
							: userTestSession.getSectionsNoOfQuestionsNotAnswered()) + ", "
							+ sectionInstanceDto.getSection().getSectionName() + "-"
							+ sectionInstanceDto.getNoOfQuestionsNotAnswered());
		}
		if (userTestSession.getSectionResults().startsWith(",")) {
			userTestSession.setSectionResults(userTestSession.getSectionResults().replaceFirst(",", ""));
		}

		if (userTestSession.getSectionsNoOfQuestionsNotAnswered() != null
				&& userTestSession.getSectionsNoOfQuestionsNotAnswered().startsWith(",")) {
			userTestSession.setSectionsNoOfQuestionsNotAnswered(
					userTestSession.getSectionsNoOfQuestionsNotAnswered().replaceFirst(",", ""));
		}

		StudentTestForm studentTestForm = (StudentTestForm) request.getSession().getAttribute("studentTestForm");
		userTestSession.setTestInviteSent(studentTestForm.getTestInviteSent());
		userTestSession.setSharedDirect(studentTestForm.getSharedDirect());
		Date createDate = (Date) request.getSession().getAttribute("testStartDate");
		userTestSession.setCreateDate(createDate);
		userTestSession.setUpdateDate(new Date());
		Integer noOfAttempts = (Integer) request.getSession().getAttribute("noOfAttempts");
		userTestSession.setNoOfAttempts(noOfAttempts);
		studentTestForm.setNoOfAttempts(noOfAttempts);

		userTestSession = userTestSessionService.saveOrUpdate(userTestSession);

		/**
		 * Send the results to message queue
		 */

		/**
		 * end send results to message queue
		 */
		// studentTestForm.setNoOfAttempts(userTestSession.getNoOfAttempts());

		putMiscellaneousInfoInModel(model, request);
		setTimeInCounter(request, Long.valueOf(timeCounter));
		try {
			request.getSession().setAttribute("submitted", true);
			// String rows = sendResultsEmail(request, userTestSession);
//			sendResultsEmail(request, userTestSession);
			String rows = compileRows(request);
			model = new ModelAndView("studentTestCompletion");
			model.addObject("rows", rows);
			model.addObject("showResults", test.getSentToStudent());
			model.addObject("justification", test.getJustification());
			model.addObject("studentTestForm", studentTestForm);

//			gul
			model.addObject("showResults", true);
			model.addObject("TOTAL_QUESTIONS", userTestSession.getTotalMarks());
			model.addObject("TOTAL_MARKS", userTestSession.getTotalMarksRecieved());
			model.addObject("PASS_PERCENTAGE", test.getPassPercent());
			model.addObject("RESULT_PERCENTAGE", userTestSession.getPercentageMarksRecieved());
//			int per2 = Math.round(userTestSession.getPercentageMarksRecieved());
//			model.addObject("RESULT_PERCENTAGE_WITHOUT_FRACTION", new Integer(per2));
			model.addObject("STATUS", test.getPassPercent() > (userTestSession.getWeightedScorePercentage() == null ? 0
					: userTestSession.getWeightedScorePercentage()) ? "Fail" : "Success");

//			gul test
			model.addObject("sectionInstanceDtos", sectionInstanceDtos);
//			
			if (test.getSentToStudent()) {
				model.addObject("TOTAL_QUESTIONS", userTestSession.getTotalMarks());
				model.addObject("TOTAL_MARKS", userTestSession.getTotalMarksRecieved());
				model.addObject("PASS_PERCENTAGE", test.getPassPercent());
				model.addObject("RESULT_PERCENTAGE", userTestSession.getPercentageMarksRecieved());
				int per = Math.round(userTestSession.getPercentageMarksRecieved());
				model.addObject("RESULT_PERCENTAGE_WITHOUT_FRACTION", new Integer(per));
				// model.addObject("STATUS", test.getPassPercent() >
				// userTestSession.getPercentageMarksRecieved()?"Fail":"Success");
				model.addObject("STATUS",
						test.getPassPercent() > (userTestSession.getWeightedScorePercentage() == null ? 0
								: userTestSession.getWeightedScorePercentage()) ? "Fail" : "Success");
				model.addObject("codingAssignments", codingAssignments);

				model.addObject("sectionInstanceDtos", sectionInstanceDtos);
			}

			if (test.getJustification() != null && test.getJustification()) {
				/**
				 * Add code for showing justification grid
				 */
				model.addObject("sectionInstanceDtos", sectionInstanceDtos);
			}

			if (test.getConsiderConfidence() != null && test.getConsiderConfidence()) {
				// get confidence percent
				model.addObject("confidencePercent", confidencePercent);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			String message = "Results can not be sent for " + user.getEmail() + " for test " + test.getTestName();
//			EmailGenericMessageThread client = new EmailGenericMessageThread("jatin.sutaria@thev2technologies.com", "Can not send Test link email", message,
//					propertyConfig);
//			Thread th = new Thread(client);
//			th.start();
		}
		return model;
	}

	private String compileRows(HttpServletRequest request) {
		String table = "<tr>" + "<td>$SECTION_NAME$</td>" + "<td>" + "<div class=\"progress\">"
				+ "<div class=\"progress-bar\" role=\"progressbar\" style=\"width: $per$%;\" aria-valuenow=\"25\" aria-valuemin=\"0\" aria-valuemax=\"100\">"
				+ "$per$%</div>" + "</div>" + "</td>" + "</tr>";
		String rows = "";
		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");
		DecimalFormat df = new DecimalFormat("##.##");
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			String record = table;
			Integer per = new Integer(Math.round((float) sectionInstanceDto.getTotalCorrectAnswers().intValue()
					/ (float) sectionInstanceDto.getNoOfQuestions().intValue() * 100));
			record = record.replace("$SECTION_NAME$", sectionInstanceDto.getSection().getSectionName());
			record = record.replace("$per$", df.format(per));
			rows += record;
		}
		return rows;
	}

	private void saveSection(SectionInstanceDto currentSection, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			int totalSectionQuestions = sectionInstanceDto.getQuestionInstanceDtos().size();
			int correctAnswersPerSection = 0;
			int noOfQuestionsNotAnswered = 0;
			if (sectionInstanceDto.getSection().getSectionName().equals(currentSection.getSection().getSectionName())) {
				SectionInstance sectionInstance = new SectionInstance();
				sectionInstance.setCompanyId(user.getCompanyId());
				sectionInstance.setCompanyName(user.getCompanyName());
				sectionInstance.setTestName(test.getTestName());
				sectionInstance.setSectionName(sectionInstanceDto.getSection().getSectionName());
				sectionInstance.setStartTime(System.currentTimeMillis());
				sectionInstance.setEndTime(System.currentTimeMillis() + 200000);
				sectionInstance.setUser(user.getEmail());
				List<QuestionMapperInstance> questionMapperInstances = new ArrayList<>();

				/**
				 * Added on 13 May2020 because if in a multi section test, user directly goes to
				 * final section and then submit test then its possible then sections between 1
				 * and last may not have QuestionInstanceDtos with them
				 */
				if (sectionInstanceDto.getQuestionInstanceDtos().size() == 0) {
					populateWithQuestions(currentSection, test.getTestName(),
							currentSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				}
				/**
				 * End Added on 13 May2020
				 */

				for (QuestionInstanceDto questionInstanceDto : sectionInstanceDto.getQuestionInstanceDtos()) {
					questionInstanceDto.getQuestionMapperInstance().setCompanyId(user.getCompanyId());
					questionInstanceDto.getQuestionMapperInstance().setCompanyName(user.getCompanyName());
					questionInstanceDto.getQuestionMapperInstance().setTestName(test.getTestName());
					questionInstanceDto.getQuestionMapperInstance()
							.setSectionName(sectionInstanceDto.getSection().getSectionName());
					questionInstanceDto.getQuestionMapperInstance().setUser(user.getEmail());
					questionMapperInstances.add(questionInstanceDto.getQuestionMapperInstance());
					if (questionInstanceDto.getQuestionMapperInstance().getCorrect()) {
						correctAnswersPerSection++;
					}

					if (!questionInstanceDto.getQuestionMapperInstance().getAnswered()) {
						noOfQuestionsNotAnswered++;
					}
				}

				sectionInstance.setResults("total-" + totalSectionQuestions + ",correct-" + correctAnswersPerSection);
				sectionInstance.setNoOfQuestionsNotAnswered(noOfQuestionsNotAnswered);
				// sectionInstanceService.saveSection(sectionInstance, questionMapperInstances);
				/**
				 * Save only section instance
				 */
				// sectionInstanceService.saveSectionOnly(sectionInstance);
				sectionInstanceService.addOnlyIfAnswersNotPresent(sectionInstance, questionMapperInstances);
				/**
				 * End Save only section instance
				 */

				sectionInstanceDto.setNoOfQuestions(totalSectionQuestions);
				sectionInstanceDto.setTotalCorrectAnswers(correctAnswersPerSection);
				sectionInstanceDto.setNoOfQuestionsNotAnswered(noOfQuestionsNotAnswered);
			}
		}
	}

	private SectionInstanceDto getCurrentSection(Long qMapperId, List<SectionInstanceDto> sectionInstanceDtos,
			SectionInstanceDto currentSection) {
		QuestionMapper mapper = questionMapperRep.findById(qMapperId).get();
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			if (sectionInstanceDto.getSection().getSectionName().equals(mapper.getSectionName())) {
				return sectionInstanceDto;
			}
		}
		return currentSection;
	}

	private Boolean isQuestionLast(QuestionInstanceDto current, QuestionSequence questionSequence,
			SectionSequence sectionSequence) {
		if (sectionSequence
				.nextSection(current.getQuestionMapperInstance().getQuestionMapper().getSectionName()) == null) {
			// means this is the last section
			if (questionSequence
					.nextQuestion(current.getQuestionMapperInstance().getQuestionMapper().getId()) == null) {
				return true;
			}
		}
		return false;
	}

	private Boolean isQuestionFirst(QuestionInstanceDto current, QuestionSequence questionSequence,
			SectionSequence sectionSequence) {
		if (sectionSequence
				.prevSection(current.getQuestionMapperInstance().getQuestionMapper().getSectionName()) == null) {
			// means this is the first section
			if (questionSequence
					.previousQuestion(current.getQuestionMapperInstance().getQuestionMapper().getId()) == null) {
				return true;
			}
		}
		return false;
	}

	private void setAnswers(HttpServletRequest request, SectionInstanceDto currentSection,
			QuestionInstanceDto currentQuestion, String questionMapperId, Boolean calledFromSubmit) {

		List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession()
				.getAttribute("sectionInstanceDtos");
		for (SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			if (sectionInstanceDto.getSection().getSectionName().equals(currentSection.getSection().getSectionName())) {

				for (QuestionInstanceDto questionInstanceDto : currentSection.getQuestionInstanceDtos()) {

					if (questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getId()
							.equals(Long.valueOf(questionMapperId))) {

						User user = (User) request.getSession().getAttribute("user");
						Test test = (Test) request.getSession().getAttribute("test");
						questionInstanceDto.getQuestionMapperInstance().setCompanyId(user.getCompanyId());
						questionInstanceDto.getQuestionMapperInstance().setCompanyName(user.getCompanyName());
						questionInstanceDto.getQuestionMapperInstance().setUser(user.getEmail());
						questionInstanceDto.getQuestionMapperInstance()
								.setUerFullName(user.getFirstName() + " " + user.getLastName());
						questionInstanceDto.getQuestionMapperInstance()
								.setSectionName(currentSection.getSection().getSectionName());
						questionInstanceDto.getQuestionMapperInstance().setTestName(test.getTestName());

						if (questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion()
								.getQuestionType() == null) {
							questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion()
									.setQuestionType(QuestionType.MCQ);
						}
						String type = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion()
								.getQuestionType().getType();
						Question q = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion();

						String userChoices = "";
						if (currentQuestion.getOne()) {
							userChoices = "Choice 1";
							questionInstanceDto.setOne(true);
						} else {
							questionInstanceDto.setOne(false);
						}

						if (currentQuestion.getTwo()) {
							if (userChoices.length() > 0) {
								userChoices += "-Choice 2";
								questionInstanceDto.setTwo(true);
							} else {
								userChoices = "Choice 2";
								questionInstanceDto.setTwo(true);
							}
						} else {
							questionInstanceDto.setTwo(false);
						}

						if (currentQuestion.getThree()) {
							if (userChoices.length() > 0) {
								userChoices += "-Choice 3";
								questionInstanceDto.setThree(true);
							} else {
								userChoices = "Choice 3";
								questionInstanceDto.setThree(true);
							}
						} else {
							questionInstanceDto.setThree(false);
						}

						if (currentQuestion.getFour()) {
							if (userChoices.length() > 0) {
								userChoices += "-Choice 4";
								questionInstanceDto.setFour(true);
							} else {
								userChoices = "Choice 4";
								questionInstanceDto.setFour(true);
							}
						} else {
							questionInstanceDto.setFour(false);
						}

						if (currentQuestion.getFive()) {
							if (userChoices.length() > 0) {
								userChoices += "-Choice 5";
								questionInstanceDto.setFive(true);
							} else {
								userChoices = "Choice 5";
								questionInstanceDto.setFive(true);
							}
						} else {
							questionInstanceDto.setFive(false);
						}

						if (currentQuestion.getSix()) {
							if (userChoices.length() > 0) {
								userChoices += "-Choice 6";
								questionInstanceDto.setSix(true);
							} else {
								userChoices = "Choice 6";
								questionInstanceDto.setSix(true);
							}
						} else {
							questionInstanceDto.setSix(false);
						}

						questionInstanceDto.getQuestionMapperInstance().setUserChoices(userChoices);
						/**
						 * Confidence based assessment
						 */
						questionInstanceDto.getQuestionMapperInstance().setConfidence(currentQuestion.getConfidence());
						questionInstanceDto.setConfidence(currentQuestion.getConfidence());
						questionInstanceDto.setRadioAnswer(currentQuestion.getRadioAnswer());
						sectionInstanceDto.setQuestionInstanceDtos(currentSection.getQuestionInstanceDtos());
						/**
						 * Question level persistence code added
						 */

						sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
						/**
						 * End Question level persistence
						 */
						break;
					}
				}
			}
		}
	}

	private SectionInstanceDto populateWithQuestions(SectionInstanceDto sectionInstanceDto, String testName,
			String sectionName, String companyId, String email) {
		if (sectionInstanceDto.getQuestionInstanceDtos().size() > 0) {
			return sectionInstanceDto;
		}
		List<QuestionMapper> questionMappers = questionMapperService.getQuestionsForSection(testName, sectionName,
				companyId);
		List<QuestionMapper> questionMappersActual = questionMappers.subList(0, sectionInstanceDto.getNoOfQuestions());
		int pos = 0;
		for (QuestionMapper questionMapper : questionMappersActual) {
			QuestionInstanceDto questionInstanceDto = new QuestionInstanceDto();
			pos++;
			questionInstanceDto.setPosition(pos);
			// QuestionMapperInstance questionMapperInstance = new QuestionMapperInstance();
			QuestionMapperInstance questionMapperInstance = null;
			if (sectionInstanceDto.getSection().getPercentQuestionsAsked() == 100) {
				questionMapperInstance = questionMapperInstaceService.removeDublicateAndGetInstance(
						questionMapper.getQuestion().getQuestionText(), testName, sectionName, email, companyId);
			}

			if (questionMapperInstance == null) {
				questionMapperInstance = new QuestionMapperInstance();
			}

			questionInstanceDto.setQuestionMapperInstance(questionMapperInstance);
			questionMapperInstance.setQuestionMapper(questionMapper);
			sectionInstanceDto.getQuestionInstanceDtos().add(questionInstanceDto);
		}
		return sectionInstanceDto;
	}

	private ModelAndView processPercentages(ModelAndView model, List<SectionInstanceDto> sectionInstanceDtos,
			int noOfQs) {
		int noOfQuestionsNotAnswered = 0;
		int noOfQuestions = noOfQs;
		for (SectionInstanceDto dto : sectionInstanceDtos) {
			if (dto.getQuestionInstanceDtos().size() == 0) {
				noOfQuestionsNotAnswered = noOfQuestionsNotAnswered + dto.getNoOfQuestions();// making sure no of qs not
																								// answered are
																								// considered for the
																								// test when
				// it begins or lese the progress would be wrong
			}
			for (QuestionInstanceDto questionInstanceDto : dto.getQuestionInstanceDtos()) {
				String qType = "";
				if (questionInstanceDto.getQuestionMapperInstance() == null
						|| questionInstanceDto.getQuestionMapperInstance().getQuestionMapper() == null
						|| questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion() == null
						|| questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion()
								.getQuestionType() == null) {
					qType = QuestionType.MCQ.getType();
				} else {
					qType = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion()
							.getQuestionType().getType();
				}

				if (qType.equalsIgnoreCase(QuestionType.MCQ.getType())) {
					if (questionInstanceDto.getOne() == false && questionInstanceDto.getTwo() == false
							&& questionInstanceDto.getThree() == false && questionInstanceDto.getFour() == false
							&& questionInstanceDto.getFive() == false && questionInstanceDto.getSix() == false) {
						noOfQuestionsNotAnswered++;
					}
				}

			}
		}
		float per = (100 * (noOfQuestions - noOfQuestionsNotAnswered)) / noOfQuestions;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		String percentage = df.format(per);
		model.addObject("percentage", percentage);
		model.addObject("totalQuestions", "" + noOfQuestions);
		model.addObject("noAnswered", "" + (noOfQuestions - noOfQuestionsNotAnswered));
		return model;
	}
}
