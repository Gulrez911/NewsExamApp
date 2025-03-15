package com.ctet.controllers;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ctet.Exceptions.AssessmentGenericException;
import com.ctet.common.CommonUtil;
import com.ctet.common.ExcelReader;
import com.ctet.data.CandidateProfileParams;
import com.ctet.data.Company;
import com.ctet.data.DifficultyLevel;
import com.ctet.data.Question;
import com.ctet.data.QuestionType;
import com.ctet.data.User;
import com.ctet.repositories.CandidateProfileParamsRepository;
import com.ctet.repositories.QuestionRepository;
import com.ctet.services.CandidateProfileParamsService;
import com.ctet.services.CompanyService;
import com.ctet.services.QuestionService;

@Controller
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	CandidateProfileParamsService profileService;

	@Autowired
	CandidateProfileParamsRepository profileRepository;

	@Autowired
	CompanyService companyService;

	@Autowired
	QuestionRepository questionRepository;

	Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@RequestMapping(value = "/lmsQuestion_list", method = RequestMethod.GET)
	public ModelAndView listQuestions(@RequestParam(name = "page", required = false) Integer pageNumber, HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		ModelAndView mav = new ModelAndView("lms_question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		// Page<Question> questions =
		// questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
//		Page<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId(), pageNumber);

		Page<Question> questions = questionService.findByCompanyId(user.getCompanyId(), pageNumber);

		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		CommonUtil.setCommonAttributesOfPagination(questions, modelMap, pageNumber, "lmsQuestion_list", null);
		return mav;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	@ResponseBody
	public void uploadQuestions(HttpServletResponse response, MultipartHttpServletRequest request) throws Exception {
		try {
			MultipartFile multipartFile = request.getFile("fileQuestions");
//			Long size = multipartFile.getSize();
//			String fileName = multipartFile.getName();
//			String contentType = multipartFile.getContentType();
			InputStream stream = multipartFile.getInputStream();
			File file = ResourceUtils.getFile("classpath:questions.xml");
			List<Question> questions = ExcelReader.parseExcelFileToBeans(stream, file);
			logger.info("in upload method qs size " + questions.size());
			if (questions.size() == 0) {
				throw new AssessmentGenericException("NO_DATA_IN_EXCEL");
			}
			String compId = questions.get(0).getCompanyId();
			// System.out.println("comp id is "+compId);
			Company company = companyService.findByCompanyId(compId);
			// System.out.println("Company got in uploadQuestions "+company.getId() +"
			// "+company.getCompanyName());
			logger.info("Company got in uploadQuestions " + company.getId() + " " + company.getCompanyName());
			for (Question q : questions) {
				// System.out.println(q.getQuestionText());
				q.setCompanyId(company.getCompanyId());
				q.setCompanyName(company.getCompanyName());
				q.setChoice1(q.getChoice1().trim());
				q.setChoice2(q.getChoice2().trim());
				q.setChoice3(q.getChoice3() == null ? "" : q.getChoice3().trim());
				q.setChoice4(q.getChoice4() == null ? "" : q.getChoice4().trim());
				q.setChoice5(q.getChoice5() == null ? "" : q.getChoice5().trim());
				q.setChoice6(q.getChoice6() == null ? "" : q.getChoice6().trim());
				q.setRightChoices(q.getRightChoices().trim());
				questionService.createQuestion(q);
			}
			logger.info("upload qs in db complete");
//			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem in uploading qs", e);
			throw new AssessmentGenericException("problem in uploading qs", e);
//			return "Company id is not valid";
		}
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
	public ModelAndView addQuestion(@RequestParam(name = "page", required = false) Integer pageNumber, @RequestParam(name = "qid", required = false) Long qid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("add_question");

		if (pageNumber == null) {
			pageNumber = 0;
		}
		if (qid == null) {
			Question question = new Question();
			mav.addObject("question", question);
			mav.addObject("question_label", "Add new Question");
		} else {
			Question question = questionService.findById(qid);

			question.setUpFromInUpdateMode();
			mav.addObject("question", question);
			mav.addObject("editQMode", "true");
			mav.addObject("question_label", "Update this Question");
		}

		return mav;
	}

	@RequestMapping(value = "/saveQuestion", method = RequestMethod.POST)
	public ModelAndView saveQuestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("question") Question question) throws Exception {
		ModelAndView mav = null;
		User user = (User) request.getSession().getAttribute("user");
		List<Question> questions = new ArrayList<Question>();
		logger.info("in error loop");
		logger.info("before setup");
		question.setup();

		if (question.getId() != null) {
			System.out.println("before saving Qu");
			logger.info("before saving Qu");
			questionService.updateQuestion(question);

			CandidateProfileParams params = profileService.findUniqueCandidateProfileParams(question.getQualifier1(),
					question.getQualifier2() == null ? "NA" : question.getQualifier2().isEmpty() ? "NA" : question.getQualifier2(),
					question.getQualifier3() == null ? "NA" : question.getQualifier3().isEmpty() ? "NA" : question.getQualifier3(),
					question.getQualifier4() == null ? "NA" : question.getQualifier4().isEmpty() ? "NA" : question.getQualifier4(),
					question.getQualifier5() == null ? "NA" : question.getQualifier5().isEmpty() ? "NA" : question.getQualifier5());
			if (params == null) {
				CandidateProfileParams candidateProfileParam = new CandidateProfileParams();
				candidateProfileParam.setCreateDate(new Date());
				candidateProfileParam.setLESS_THAN_TWENTY_PERCENT("Need to work a bit. Not good!");
				candidateProfileParam.setBETWEEN_TWENTY_AND_FIFTY("Knowledge Quotient inadequate for the given topic!!");
				candidateProfileParam.setBETWEEN_FIFTY_AND_SEVENTYFIVE("Average Grade for a given topic!!");
				candidateProfileParam.setBETWEEN_SEVENTYFIVE_AND_NINETY("Good knowledge. Potential to be an expert in this area!!");
				candidateProfileParam.setMORE_THAN_NINETY("Excellent knowledge on the theory behind the topic.");
				candidateProfileParam.setQualifier1(question.getQualifier1());
				candidateProfileParam
						.setQualifier2(question.getQualifier2() == null ? "NA" : question.getQualifier2().isEmpty() ? "NA" : question.getQualifier2());
				candidateProfileParam
						.setQualifier3(question.getQualifier3() == null ? "NA" : question.getQualifier3().isEmpty() ? "NA" : question.getQualifier3());
				candidateProfileParam
						.setQualifier4(question.getQualifier4() == null ? "NA" : question.getQualifier4().isEmpty() ? "NA" : question.getQualifier4());
				candidateProfileParam
						.setQualifier5(question.getQualifier5() == null ? "NA" : question.getQualifier5().isEmpty() ? "NA" : question.getQualifier5());
				profileRepository.save(candidateProfileParam);
			}
			System.out.println("before saving Qu");

			logger.info("before saving Qu");
		} else {
			logger.info("before saving Q");
			question.setCompanyId(user.getCompanyId());
			question.setCompanyName(user.getCompanyName());
			questionService.createQuestion(question);
			CandidateProfileParams params = profileService.findUniqueCandidateProfileParams(question.getQualifier1(),
					question.getQualifier2() == null ? "NA" : question.getQualifier2().isEmpty() ? "NA" : question.getQualifier2(),
					question.getQualifier3() == null ? "NA" : question.getQualifier3().isEmpty() ? "NA" : question.getQualifier3(),
					question.getQualifier4() == null ? "NA" : question.getQualifier4().isEmpty() ? "NA" : question.getQualifier4(),
					question.getQualifier5() == null ? "NA" : question.getQualifier5().isEmpty() ? "NA" : question.getQualifier5());
			if (params == null) {
				CandidateProfileParams candidateProfileParam = new CandidateProfileParams();
				candidateProfileParam.setCreateDate(new Date());
				candidateProfileParam.setLESS_THAN_TWENTY_PERCENT("Need to work a bit. Not good!");
				candidateProfileParam.setBETWEEN_TWENTY_AND_FIFTY("Knowledge Quotient inadequate for the given topic!!");
				candidateProfileParam.setBETWEEN_FIFTY_AND_SEVENTYFIVE("Average Grade for a given topic!!");
				candidateProfileParam.setBETWEEN_SEVENTYFIVE_AND_NINETY("Good knowledge. Potential to be an expert in this area!!");
				candidateProfileParam.setMORE_THAN_NINETY("Excellent knowledge on the theory behind the topic.");
				candidateProfileParam.setQualifier1(question.getQualifier1());
				candidateProfileParam
						.setQualifier2(question.getQualifier2() == null ? "NA" : question.getQualifier2().isEmpty() ? "NA" : question.getQualifier2());
				candidateProfileParam
						.setQualifier3(question.getQualifier3() == null ? "NA" : question.getQualifier3().isEmpty() ? "NA" : question.getQualifier3());
				candidateProfileParam
						.setQualifier4(question.getQualifier4() == null ? "NA" : question.getQualifier4().isEmpty() ? "NA" : question.getQualifier4());
				candidateProfileParam
						.setQualifier5(question.getQualifier5() == null ? "NA" : question.getQualifier5().isEmpty() ? "NA" : question.getQualifier5());
				candidateProfileParam.setCompanyId(user.getCompanyId());
				candidateProfileParam.setCompanyName(user.getCompanyName());
				profileRepository.save(candidateProfileParam);
			}
			logger.info("after saving Q");
		}

		mav = new ModelAndView("add_question");
		mav.addObject("message", "Question Save Success");// later put it as label
		mav.addObject("msgtype", "Success");
		mav.addObject("icon", "success");
		// return listQuestions(null, response, request, mav.getModelMap());
		question = new Question();
		question.setType(question.getQuestionType() != null ? question.getQuestionType().getType() : QuestionType.MCQ.getType());

		mav.addObject("question", question);
		mav.addObject("question_label", "Add new Question");
		logger.info("after saving Q ...1");
		Page<Question> questions2 = questionService.findQuestionsByPage(0);
		logger.info("after saving Q ...2");
		mav.addObject("qs", questions2.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		logger.info("after saving Q ...3");
//		CommonUtil.setCommonAttributesOfPagination(questions2, mav.getModelMap(), 0, "addQuestion", null);
		logger.info("after saving Q ...4");
		return mav;
	}

	@RequestMapping(value = "/searchQuestions", method = RequestMethod.GET)
	public ModelAndView searchQuestions(@RequestParam(name = "page", required = false) Integer pageNumber, @RequestParam String searchText, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_question_list");
		User user = (User) request.getSession().getAttribute("user");

		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.searchQuestions(user.getCompanyId(), searchText, pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		Map<String, String> params = new HashMap<>();
		params.put("searchText", searchText);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber, "lmsQuestion_list", params);
		return mav;
	}
	
	@RequestMapping(value = "/removeQuestion", method = RequestMethod.GET)
	public ModelAndView removeQuestion(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam(name = "qid", required = false) Long qid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("lms_question_list");
		if (qid != null) {
			Boolean canDelete = questionService.canDeleteQuestion(qid);
			if (canDelete) {
				questionService.removeQuestion(qid);
				mav.addObject("icon", "success");
				mav.addObject("message", "Question successfully deleted");// later put it as label
				mav.addObject("msgtype", "success");
			} else {
				mav.addObject("icon", "warning");
				mav.addObject("message", "This Question is associated with one or more Tests. Can not delete this Q");// later
													 
				mav.addObject("msgtype", "failure");
			}
		} else {
			mav.addObject("message", "Nothing to remove");// later put it as label
			mav.addObject("msgtype", "failure");
		}

		Question question = new Question();
		mav.addObject("question", question);
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		// Page<Question> questions =
		// questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<Question> questions = questionService.findByCompanyId(user.getCompanyId(), pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber, "lmsQuestion_list", null);
		return mav;
	}
}
