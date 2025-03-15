package com.ctet.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ctet.data.Category;
import com.ctet.data.Exam;
import com.ctet.data.Test;
import com.ctet.data.User;
import com.ctet.repositories.CategoryRepository;
import com.ctet.repositories.ExamRepository;
import com.ctet.services.CategoryService;
import com.ctet.services.ExamService;
import com.ctet.services.TestService;

@Controller
public class HomeController {

	@Autowired
	TestService testService;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ExamService examService;

	@RequestMapping(value = { "/homeExam" }, method = RequestMethod.GET)
	public ModelAndView newHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("homeExam");
		return mav;
	}

	@RequestMapping(value = { "/createExam" }, method = RequestMethod.GET)
	public ModelAndView createExame(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "eid", required = false) Long eid, @ModelAttribute("exam") Exam exam) {
		ModelAndView mav = new ModelAndView("createExam");
		User user = (User) request.getSession().getAttribute("user");
//		Exam exam = new Exam();

		if (eid != null) {
			exam = examRepository.getTestsByExamId(eid, user.getCompanyId());
			exam.setTests2(exam.getTests2());
		}

		List<Test> tests = testService.getTestNames(user.getCompanyId());
		mav.addObject("tests", tests);
		mav.addObject("exam", exam);
		return mav;
	}

	@RequestMapping(value = { "/listExam" }, method = RequestMethod.GET)
	public ModelAndView listExam(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("listExam");
		User user = (User) request.getSession().getAttribute("user");

		List<Exam> exams = examRepository.getListExams(user.getCompanyId());

		mav.addObject("exams", exams);
		return mav;
	}

	@RequestMapping(value = { "/saveExam" }, method = RequestMethod.POST)
	public ModelAndView saveExame(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("exam") Exam exam) {
//		ModelAndView mav = new ModelAndView("fake");
		ModelAndView mav = new ModelAndView("listExam");
		User user = (User) request.getSession().getAttribute("user");
		exam.setCompanyId(user.getCompanyId());
		exam.setCompanyName(user.getCompanyName());

		List<Test> tests = new ArrayList<>();
		for (String test : exam.getTests()) {
			Test test2 = testService.findbyTest(test, user.getCompanyId());
			tests.add(test2);
		}

		exam.setTests2(tests);
		examService.saveOrUpdate(exam);

		System.out.println("save......");
		List<Exam> exams = examRepository.getListExams(user.getCompanyId());

		mav.addObject("exams", exams);
		return mav;
	}

	@RequestMapping(value = { "/createCategory" }, method = RequestMethod.GET)
	public ModelAndView createCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cid", required = false) Long cid, @ModelAttribute("category") Category category) {
		ModelAndView mav = new ModelAndView("createCategory");
		User user = (User) request.getSession().getAttribute("user");

		if (cid != null) {
			category = categoryRepository.getExamsByCategoryId(cid, user.getCompanyId());
			category.setExams(category.getExams());
		}
		List<Exam> exList = examRepository.getListExams(user.getCompanyId());
		mav.addObject("exList", exList);
		mav.addObject("category", category);
		return mav;
	}

	@RequestMapping(value = { "/saveCategory" }, method = RequestMethod.POST)
	public ModelAndView saveCategory(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("category") Category category) {
		ModelAndView mav = new ModelAndView("listCategory");
		User user = (User) request.getSession().getAttribute("user");

//		if (category.getId() != null) {
//			category.setId(category.getId());
//			Mapper mapper = new DozerBeanMapper();
//			mapper.map(category, category2);
//			categoryRepository.save(category2);
//		} else {
		category.setCompanyId(user.getCompanyId());
		category.setCompanyName(user.getCompanyName());
		List<Exam> exams = new ArrayList<>();
		for (String exam : category.getExList()) {
			Exam exam2 = examRepository.findByPrimaryKey(exam, user.getCompanyId());
			exams.add(exam2);
		}
		category.setExams(exams);
//		categoryRepository.save(category);

		categoryService.saveOrUpdate(category);

//		}
		System.out.println("save......category");

		List<Category> categories = categoryRepository.getListCategory(user.getCompanyId());

		mav.addObject("categories", categories);
		return mav;
	}

	@RequestMapping(value = { "/listCategory" }, method = RequestMethod.GET)
	public ModelAndView listCategory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("listCategory");
		User user = (User) request.getSession().getAttribute("user");

		List<Category> categories = categoryRepository.getListCategory(user.getCompanyId());

		mav.addObject("categories", categories);
		return mav;

	}

	@RequestMapping(value = { "/deleteCategory" }, method = RequestMethod.GET)
	public ModelAndView deleteCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cid", required = false) Long cid) {
		ModelAndView mav = new ModelAndView("listCategory");
		User user = (User) request.getSession().getAttribute("user");

		categoryRepository.deleteById(cid);
		List<Category> categories = categoryRepository.getListCategory(user.getCompanyId());
		mav.addObject("categories", categories);
		return mav;

	}

	@RequestMapping(value = { "/updateCategory" }, method = RequestMethod.GET)
	public ModelAndView updateCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cid", required = false) Long cid) {
		ModelAndView mav = new ModelAndView("createCategory");
		User user = (User) request.getSession().getAttribute("user");
		Category category = categoryRepository.getExamsByCategoryId(cid, user.getCompanyId());
		category.setExams(category.getExams());

		List<Exam> exList = examRepository.getListExams(user.getCompanyId());
		mav.addObject("exList", exList);
		mav.addObject("category", category);
		return mav;

	}

	@RequestMapping(value = { "/deleteExam" }, method = RequestMethod.GET)
	public ModelAndView deleteExam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "eid", required = false) Long eid) {
		ModelAndView mav = new ModelAndView("listExam");
		User user = (User) request.getSession().getAttribute("user");
//		Category category = new Category();
//		category.getExams().get(0).getId();

		List<Category> categories = categoryRepository.findCategoryByExam(eid);
		System.out.println("///   " + categories);

		List<Category> categories2 = categoryRepository.getListCategory(user.getCompanyId());
		System.out.println("///   " + categories2);
		int flag = 0;
		for (Category category : categories2) {
			for (Exam exam : category.getExams()) {
				Long long1 = exam.getId();
				if (long1.equals(eid)) {
					flag = 1;
				}
			}
		}
		if (flag != 1) {
			examRepository.deleteById(eid);

			mav.addObject("message", "Exam successful deleted");// later put it as label
			mav.addObject("msgtype", "Success");
			mav.addObject("icon", "success");
		} else {

			mav.addObject("icon", "warning");
			mav.addObject("message", "Exam Cannot delete");// later
			mav.addObject("msgtype", "Failure");
		}

		List<Exam> exams = examRepository.getListExams(user.getCompanyId());
		mav.addObject("exams", exams);
		return mav;

	}

//	@RequestMapping(value = { "/updateExam" }, method = RequestMethod.GET)
//	public ModelAndView updateExam(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(name = "cid", required = false) Long cid) {
//		ModelAndView mav = new ModelAndView("createExam");
//		User user = (User) request.getSession().getAttribute("user");
//
//		Category category = categoryRepository.getExamsByCategoryId(cid, user.getCompanyId());
//		category.setExams(category.getExams());
//
//		List<Exam> exList = examRepository.getListExams(user.getCompanyId());
//		mav.addObject("exList", exList);
//		mav.addObject("category", category);
//		return mav;
//
//	}

}