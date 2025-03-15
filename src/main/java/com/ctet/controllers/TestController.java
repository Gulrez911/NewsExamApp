package com.ctet.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctet.data.DifficultyLevel;
import com.ctet.data.Question;
import com.ctet.data.QuestionMapper;
import com.ctet.data.QuestionType;
import com.ctet.data.Section;
import com.ctet.data.Test;
import com.ctet.data.User;
import com.ctet.data.UserType;
import com.ctet.repositories.QuestionRepository;
import com.ctet.services.QuestionMapperInstanceService;
import com.ctet.services.QuestionService;
import com.ctet.services.SectionService;
import com.ctet.services.TestService;
import com.ctet.services.UserService;
import com.ctet.web.dto.PDFImage;
import com.ctet.web.dto.SectionDto;
import com.google.gson.Gson;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

@Controller
public class TestController {

	@Autowired
	TestService testService;

	@Autowired
	QuestionService questionService;

	@Autowired
	SectionService sectionService;

	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;

	@Autowired
	UserService userService;
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	TestController testController;

	// delete

	private static String UPLOADED_FOLDER = "C:\\Users\\gulfa\\OneDrive\\Desktop\\Temp\\";

	@GetMapping("/uploadtest2")
	public ModelAndView uploadtest2() {
		ModelAndView mav = new ModelAndView("uploadtest");

		return mav;
	}

//	@PostMapping("/uploadtest")
//	public ModelAndView uploadtest(@RequestParam(name = "name", required = false) MultipartFile file, Model model)
//			throws IllegalStateException, java.io.IOException {
//		ModelAndView mav = new ModelAndView("uploadtest");
//		if (!file.getOriginalFilename().isEmpty()) {
////			System.out.println("testing: " + config.getFileLocation() + "/" + file.getOriginalFilename());
////			FileCopyUtils.copy(file.getBytes(), new FileOutputStream(config.getFileLocation() + "/" + file.getOriginalFilename()));
//			String destination = file.getOriginalFilename();
//			File file2 = new File(destination);
////			FileUtils.copyFile(file2, file2);
//			file.transferTo(file2);
//
//			model.addAttribute("msg", "File uploaded successfully");
//		} else {
//			model.addAttribute("msg", "Please select valid file.");
//		}
//		return mav;
//	}

	// @RequestMapping(value = "/upload", method = RequestMethod.POST)
	@PostMapping("/uploadtest") // //new annotation since 4.3
//	@ResponseBody
	public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView("uploadtestPDF");
		PDFImage pdfImage = new PDFImage();
		if (file.isEmpty()) {
//			redirectAttributes.addFlashAttribute("message", "Please select a file to upload")
			mav.setViewName("uploadtest");
			return mav;
		}
		String loc = "";
		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			pdfImage.setLoc(path.toString());

			mav.addObject("pdfImage", file.getOriginalFilename());
//			redirectAttributes.addFlashAttribute("message",
//					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
//			e.printStackTrace();
		}

		return mav;
	}

//    @RequestMapping(value = "/download22", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> getDownloadData() throws Exception {
//
//        String regData = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
//        byte[] output = regData.getBytes();
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("charset", "utf-8");
//        responseHeaders.setContentType(MediaType.valueOf("text/html"));
//        responseHeaders.setContentLength(output.length);
//        responseHeaders.set("Content-disposition", "attachment; filename=filename.txt");
//
//        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
//    }

	@GetMapping("/downloadQuestion")
	public ModelAndView downloadQuestion(HttpServletRequest request, @RequestParam(name = "loc", required = false) String loc) {
//		List<Question> listquestion = null;
//
		System.out.println("loc " + loc);
//		return new ModelAndView("downloadExcel", "listquestion", listquestion);

//		File imageFile = new File("C:\\Users\\gulfa\\OneDrive\\Desktop\\WhatsApp Image 2022-04-03 at 12.17.43 PM.jpeg");
		File imageFile = new File("C:/Users/gulfa/OneDrive/Desktop/Temp/"+loc);
		System.out.println("imageFile::: " + imageFile);
		return new ModelAndView("downloadExcel", "imageFile", imageFile);

	}

//	@GetMapping("downloadFile")
//	public @ResponseBody void getImageAsByteArray(HttpServletResponse response) throws IOException {
//		byte data[] = FileUtils.readFileToByteArray(new File("C:\\Users\\gulfa\\OneDrive\\Desktop\\nicky.jpeg"));
//		HttpHeaders headers = new HttpHeaders();
//		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//		FileCopyUtils.copy(data, response.getOutputStream());
//	}

	@GetMapping("downloadFile")
	public @ResponseBody void getImageAsByteArray(HttpServletResponse response) throws IOException {
		byte data[] = FileUtils.readFileToByteArray(new File("C:\\Users\\gulfa\\OneDrive\\Desktop\\nicky.jpeg"));
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		FileCopyUtils.copy(data, response.getOutputStream());
	}
	// delete

	@GetMapping("/lmsTests")
	public ModelAndView lmstestlist(@RequestParam(name = "page", required = false) Integer pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		User user = (User) request.getSession().getAttribute("user");
		mav = new ModelAndView("lms_test_list");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Test> tests = testService.findByCompanyId(user.getCompanyId(), pageNumber);
		mav.addObject("tests", testService.populateWithPublicUrl(tests.getContent()));
		List<Test> listTest = testService.findByCompanyId(user.getCompanyId());
		List<Test> tests2 = new ArrayList<>();
		int x = 1;
		for (Test test : listTest) {
			System.out.println(test.getCategory() + " : " + test.getcDate() + " : " + test.getuDate() + " : "
					+ test.getPublicUrl());
			Test test2 = new Test();
			test2.setSrNo(x);
			test2.setTestName(test.getTestName());
			test2.setCategory(test.getCategory());
			test2.setTestTimeInMinutes(test.getTestTimeInMinutes());
			test2.setPassPercent(test.getPassPercent());
			test2.setcDate(test.getcDate());
			test2.setuDate(test.getuDate());
			test2.setId(test.getId());
			tests2.add(test2);
			x++;
		}
		Gson gson = new Gson();
		String listTests = gson.toJson(tests2);
		mav.addObject("listTests", listTests);

//		CommonUtil.setCommonAttributesOfPagination(tests, mav.getModelMap(), pageNumber, "lmsTests", null);
		return mav;
	}

	@RequestMapping(value = "/lmsRetireTest", method = RequestMethod.GET)
	public ModelAndView retireTest(@RequestParam String testId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_test_list");
		User user = (User) request.getSession().getAttribute("user");
		testService.removeTest(user.getCompanyId(), Long.valueOf(testId));
		List<Test> listTest = testService.findByCompanyId(user.getCompanyId());
		List<Test> tests2 = new ArrayList<>();
		int x = 1;
		for (Test test : listTest) {
			System.out.println(test.getCategory() + " : " + test.getcDate() + " : " + test.getuDate() + " : "
					+ test.getPublicUrl());
			Test test2 = new Test();
			test2.setSrNo(x);
			test2.setTestName(test.getTestName());
			test2.setCategory(test.getCategory());
			test2.setTestTimeInMinutes(test.getTestTimeInMinutes());
			test2.setPassPercent(test.getPassPercent());
			test2.setcDate(test.getcDate());
			test2.setuDate(test.getuDate());
			test2.setId(test.getId());
			tests2.add(test2);
			x++;
		}
		Gson gson = new Gson();
		String listTests = gson.toJson(tests2);
		mav.addObject("listTests", listTests);

		mav.addObject("message", "Test deleted successfully !");
		mav.addObject("msgtype", "Information");
		mav.addObject("icon", "success");
		return mav;
	}

	@RequestMapping(value = "/lmsAddtest", method = RequestMethod.GET)
	public ModelAndView addtest(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		mav = new ModelAndView("lms_add_test");
		Test test = new Test();
		mav.addObject("test", test);
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		request.getSession().setAttribute("test", test);
		SectionDto sectionDto = new SectionDto();
		sectionDto.setCurrent(true);
		sectionDto.setSectionNo(1);
		sectionDto.setCompanyId(user.getCompanyId());
		sectionDto.setTestName(test.getTestName());
		request.getSession().setAttribute("sectionDTO", sectionDto);
		String testTypes[] = { "Java", "Microsoft technologies", "C/C++", "Python", "General Knowledge",
				"Composite Test" };
		mav.addObject("testTypes", testTypes);
		// mav.addObject("qs", questions);
		return mav;
	}

	@RequestMapping(value = "/lmsUpdateTest", method = RequestMethod.GET)
	public ModelAndView updateTest(@RequestParam String testId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test");
		User user = (User) request.getSession().getAttribute("user");
		// testService.f
		Test test = testService.findTestById(Long.valueOf(testId));
		mav.addObject("test", test);
		mav.addObject("user", user);
		String testTypes[] = { "Java", "Microsoft technologies", "C/C++", "Python", "General Knowledge",
				"Composite Test" };
		mav.addObject("testTypes", testTypes);
		request.getSession().setAttribute("test", test);
		return mav;
	}

	@RequestMapping(value = "/lmsGobackStep1Test", method = RequestMethod.GET)
	public ModelAndView gobackStep1Test(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test");
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		mav.addObject("user", user);
		mav.addObject("test", test);
		String testTypes[] = { "Java", "Microsoft technologies", "C/C++", "Python", "General Knowledge",
				"Composite Test" };
		mav.addObject("testTypes", testTypes);
		return mav;
	}

	@RequestMapping(value = "/removeQuestionToSectionAjax", method = RequestMethod.GET)
	public @ResponseBody String removeQuestionToSectionAjax(@RequestParam String sectionName,
			@RequestParam String questionId, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		Question delete = new Question();
		delete.setId(Long.valueOf(questionId));
		sectionDto.getQuestions().remove(delete);
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
//		List<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId());
		process(qs, sectionDto);
		return "" + sectionDto.getQuestions().size();
	}

	@RequestMapping(value = "/showParentCategories", method = RequestMethod.GET)
	public ModelAndView showParentCategories(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2_new3");
		User user = (User) request.getSession().getAttribute("user");
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
		Test test = (Test) request.getSession().getAttribute("test");
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		mav.addObject("user", user);
		mav.addObject("sectionDto", sectionDto);
		mav.addObject("test", test);
		mav.addObject("qs", process(qs, sectionDto));
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		return mav;
	}

	@RequestMapping(value = "/lmsAddNewSection", method = RequestMethod.GET)
	public ModelAndView addNewSection(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		Test test = (Test) request.getSession().getAttribute("test");
		List<SectionDto> sectionDtos = test.getSectionDtos();
		for (SectionDto dto : sectionDtos) {
			dto.setCurrent(false);
		}
		SectionDto dto = new SectionDto();
		dto.setCompanyId(user.getCompanyId());
		dto.setTestName(test.getTestName());
		dto.setSectionNo(sectionDtos.size() + 1);
		dto.setSectionName("Section " + (sectionDtos.size() + 1));
		dto.setCurrent(true);
		dto.setNoOfQuestions(0);
		sectionDtos.add(dto);
		request.getSession().setAttribute("sectionDTO", dto);
		mav.addObject("test", test);
		mav.addObject("sectionDto", dto);
		// List<Question> qs = questionService.findQuestions(user.getCompanyId());
		List<Question> qs = questionService.getAllLevel1Questions(user.getCompanyId());
		mav.addObject("qs", qs);
		return mav;
	}

	@RequestMapping(value = "/lmsGoToSection", method = RequestMethod.GET)
	public ModelAndView goToSection(@RequestParam String sectionName, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2_new3");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		Test test = (Test) request.getSession().getAttribute("test");
		List<SectionDto> sectionDtos = test.getSectionDtos();
		for (SectionDto dto : sectionDtos) {
			dto.setCurrent(false);
			if (dto.getSectionName().equals(sectionName)) {
				dto.setCurrent(true);
				request.getSession().setAttribute("sectionDTO", dto);
				mav.addObject("sectionDto", dto);
				List<Question> qs = questionService.findQuestions(user.getCompanyId());
//				List<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId());
				mav.addObject("qs", process(qs, dto));
				mav.addObject("test", test);
			}
		}

		return mav;
	}

	@RequestMapping(value = "/lmsSaveAndGoToStep2", method = RequestMethod.POST)
	public ModelAndView saveAndGoToStep2(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("test") Test test) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2");

		User user = (User) request.getSession().getAttribute("user");
		if (!(user.getUserType().getType().equals(UserType.ADMIN.getType())
				|| user.getUserType().getType().equals(UserType.SUPER_ADMIN.getType())
				|| user.getUserType().getType().equals(UserType.LMS_ADMIN.getType()))) {
			request.getSession().invalidate();
			mav = new ModelAndView("login_new_2");
			user = new User();
			user.setEmail("admin@e-assess.com");
			user.setPassword("1234");
			user.setCompanyName("e-assess");
			mav.addObject("user", user);
			return mav;
		}

		Test test2 = (Test) request.getSession().getAttribute("test");
		if (test2.getId() != null) {
			test.setId(test2.getId());
			/**
			 * Since this is not user entered and disabled from ui - ui sends this as null.
			 */
			test.setTestName(test2.getTestName());
		} else {
			/**
			 * Make sure no body creates a test with same name again.This scenario is only
			 * applicable for new tests only.
			 */
			test.setTestName(test.getTestName().trim()); // make sure there are no leading or trailing
			// spaces in test name
			Test test3 = testService.findbyTest(test.getTestName(), user.getCompanyId());
			if (test3 != null) {
				mav = new ModelAndView("lms_add_test");

				mav.addObject("test", test);

				mav.addObject("user", user);
				request.getSession().setAttribute("test", test2);
				SectionDto sectionDto = new SectionDto();
				sectionDto.setCurrent(true);
				sectionDto.setSectionNo(1);
				sectionDto.setCompanyId(user.getCompanyId());
				sectionDto.setTestName(test.getTestName());
				request.getSession().setAttribute("sectionDTO", sectionDto);
				String testTypes[] = { "Java", "Microsoft technologies", "C/C++", "Python", "General Knowledge",
						"Composite Test" };
				mav.addObject("testTypes", testTypes);
				// mav.addObject("qs", questions);
				mav.addObject("message", "A test with the supplied test name exists! Please use a different name.");// later
				// put
				// it
				// as
				// label
				mav.addObject("msgtype", "Information");
				return mav;
			}
		}

		test.setCreatedBy(user.getEmail());
		test.setCompanyId(user.getCompanyId());
		test.setCompanyName(user.getCompanyName());
		if (test2 != null) {
			test.setTotalMarks(test2.getTotalMarks());
		}
		// Skill skill = skillService.findSkillByNameAndLevel("Java",
		// SkillLevel.BASIC.getLevel(), user.getCompanyId());
		// test.getSkills().add(skill);
		testService.saveOrUpdate(test);
		request.getSession().setAttribute("test", test);
		mav.addObject("user", user);
		mav.addObject("test", test);
		SectionDto sectionDto = null;

		List<Section> sections = sectionService.getSectionsForTest(test.getTestName(), user.getCompanyId());
		if (sections.size() == 0) {
			sectionDto = new SectionDto();
			sectionDto.setCompanyId(user.getCompanyId());
			sectionDto.setTestName(test.getTestName());
			sectionDto.setSectionName("Main Section");
			sectionDto.setSectionNo(1);
			sectionDto.setCurrent(true);
			sectionDto.setNoOfQuestions(0);
			test.getSectionDtos().add(sectionDto);

		} else {
			int count = 1;

			for (Section s : sections) {
				Section section = s;
				SectionDto dto = new SectionDto();
				dto.setSectionNo(count);
				if (count == 1) {
					dto.setCurrent(true);
				}
				count++;
				dto.setSectionId(section.getId());
				dto.setCompanyId(user.getCompanyId());
				dto.setTestName(test.getTestName());
				dto.setSectionName(section.getSectionName());
				dto.setPercentQuestionsAsked(section.getPercentQuestionsAsked());
				dto.setNoOfQuestions(s.getNoOfQuestions());
				List<QuestionMapper> questionMappers = sectionService.getQuestionsForSection(test.getTestName(),
						section.getSectionName(), user.getCompanyId());
				for (QuestionMapper mapper : questionMappers) {
					dto.getQuestions().add(mapper.getQuestion());
				}
				test.getSectionDtos().add(dto);
			}

			if (sections.size() == 0) {
				sectionDto = new SectionDto();
				sectionDto.setCompanyId(user.getCompanyId());
				sectionDto.setTestName(test.getTestName());
				sectionDto.setSectionName("Main Section");
				sectionDto.setSectionNo(1);
				sectionDto.setCurrent(true);
				test.getSectionDtos().add(sectionDto);
			}

		}
		sectionDto = test.getSectionDtos().get(0);

		request.getSession().setAttribute("sectionDTO", sectionDto);
		mav.addObject("sectionDto", sectionDto);// added here
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
//		List<Question> qs = null;

		mav.addObject("qs", process(qs, sectionDto));
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("test", test);
		return mav;
	}

	@RequestMapping(value = "/lmsAddteststep2", method = RequestMethod.GET)
	public ModelAndView addteststep2(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		mav = new ModelAndView("lms_add_test_step2");
		User user = (User) request.getSession().getAttribute("user");
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
//		List<Question> qs = questionService.getAllLevel1Questions(user.getCompanyId());
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		mav.addObject("sectionDto", sectionDto);
		if (sectionDto != null) {
			mav.addObject("qs", process(qs, sectionDto));
		} else {
			mav.addObject("qs", qs);
		}

		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		Test test = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test);
		return mav;

	}

	@RequestMapping(value = "/lmsShowSectionsQuestions", method = RequestMethod.GET)
	public ModelAndView showSectionsQuestions(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("test") Test test) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2_new3");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");

		Set<Question> questions = sectionDto.getQuestions();
		for (Question q : questions) {
			q.setSelected(true);
		}
		mav.addObject("qs", questions);

		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("sectionDto", sectionDto);
		Test test2 = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test2);
		return mav;
	}

	@RequestMapping(value = "/lmsSaveSection", method = RequestMethod.GET)
	@Transactional
	public ModelAndView saveSection(@RequestParam String sectionTopic, @RequestParam String percentage,
			HttpServletRequest request, HttpServletResponse response, @ModelAttribute("test") Test test) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);

		test = (Test) request.getSession().getAttribute("test");

		if (!(user.getUserType().getType().equals(UserType.ADMIN.getType())
				|| user.getUserType().getType().equals(UserType.SUPER_ADMIN.getType())
				|| user.getUserType().getType().equals(UserType.LMS_ADMIN.getType()))) {
			request.getSession().invalidate();
			mav = new ModelAndView("index");
			user = new User();
			user.setEmail("admin@eassess.in");
			user.setPassword("1234");
			user.setCompanyName("e-assess");
			mav.addObject("user", user);
			return mav;
		}

		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		String oldSectionName = sectionDto.getSectionName();
		for (SectionDto dto : test.getSectionDtos()) {
			if (dto.getSectionName().equals(sectionDto.getSectionName())) {
				dto.setSectionName(sectionTopic);
				dto.setPercentQuestionsAsked(Integer.parseInt(percentage));
			}
		}

		sectionDto.setSectionName(sectionTopic);
		sectionDto.setPercentQuestionsAsked(Integer.parseInt(percentage));
		Section section = null;
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
//		List<Question> qs = questionService.getAllLevel1Questions(user.getCompanyId());

		boolean edit = questionMapperInstanceService.canEditTest(sectionTopic, test.getTestName(), user.getCompanyId());
		if (!edit) {
			mav.addObject("sectionDto", sectionDto);
			mav.addObject("qs", process(qs, sectionDto));
			mav.addObject("test", test);
			mav.addObject("icon", "error");
			mav.addObject("message", "Users have started taking this test. You can't edit the test now!");// later
			// put
			// it
			// as
			// label
			mav.addObject("msgtype", "Information");
			return mav;
		}

		if (sectionDto.getSectionId() == null) {
			// create section first
			// Section existing = sectionService.findByPrimaryKey(test.getTestName(),
			// sectionDto.getSectionName(), user.getCompanyId());
			// if(existing != null) {
			if (checkMultipleSectionWithSameNames(sectionDto.getSectionName(), request)) {
				mav.addObject("sectionDto", sectionDto);
				mav.addObject("qs", process(qs, sectionDto));
				mav.addObject("test", test);
				mav.addObject("icon", "error");
				mav.addObject("message", "Section - " + sectionDto.getSectionName()
						+ " already exists for the given Test. Use a different name");// later
				// put
				// it
				// as
				// label
				mav.addObject("msgtype", "Information");
				return mav;
			}
			section = new Section();
			section.setCompanyId(user.getCompanyId());
			section.setCompanyName(user.getCompanyName());
			section.setTestName(test.getTestName());
			section.setSectionName(sectionTopic);
			section.setPercentQuestionsAsked(sectionDto.getPercentQuestionsAsked());
			sectionService.createSection(section);
			sectionDto.setSectionId(section.getId());
		} else {
			// Section existing = sectionService.findByPrimaryKey(test.getTestName(),
			// sectionDto.getSectionName(), user.getCompanyId());
			// if(existing != null) {
			if (checkMultipleSectionWithSameNames(sectionDto.getSectionName(), request)) {
				mav.addObject("sectionDto", sectionDto);
				mav.addObject("qs", process(qs, sectionDto));
				mav.addObject("test", test);
				mav.addObject("icon", "error");
				mav.addObject("message", "Section - " + sectionDto.getSectionName()
						+ " already exists for the given Test. Use a different name");// later
				// put
				// it
				// as
				// label
				mav.addObject("msgtype", "Information");
				return mav;
			}

			section = sectionService.getSectionById(sectionDto.getSectionId());
		}

		// oldSectionName
		// sectionService.removeQuestionsFromSection(section.getSectionName(),
		// section.getTestName(), user.getCompanyId());
		sectionService.removeQuestionsFromSection(oldSectionName, section.getTestName(), user.getCompanyId());

		/**
		 * V. Imp
		 */
		section.setSectionName(sectionTopic);
		section.setPercentQuestionsAsked(sectionDto.getPercentQuestionsAsked());
		sectionService.changeSectionNameAndPercent(section, sectionTopic, sectionDto.getPercentQuestionsAsked(),
				sectionDto.getQuestions().size());
		Set<Question> questions = sectionDto.getQuestions();
		for (Question question : questions) {

			sectionService.addQuestionToSection(question, section, 1);
		}
		Integer totMarks = testService.computeTestTotalMarksAndSave(test);
		test.setTotalMarks(totMarks);
		request.getSession().setAttribute("test", test);
		sectionDto.setNoOfQuestions(sectionDto.getQuestions().size());
		mav.addObject("sectionDto", sectionDto);
		mav.addObject("qs", process(qs, sectionDto));
		mav.addObject("test", test);
		mav.addObject("icon", "success");
		mav.addObject("message", "Section - " + sectionDto.getSectionName()
				+ " has been renamed and the entire section has been saved successfully.");// later
		// put
		// it
		// as
		// label
		mav.addObject("msgtype", "Information");
		return mav;

	}

	@RequestMapping(value = "/lmsShowSelectedUsers", method = RequestMethod.GET)
	public ModelAndView showSelectedUsers(HttpServletRequest request, HttpServletResponse response) {
		String referer = request.getHeader("Referer");
		ModelAndView mav = new ModelAndView("lms_add_test_step3_new");
		User user = (User) request.getSession().getAttribute("user");
		Test test = (Test) request.getSession().getAttribute("test");
		mav.addObject("users", test.getUsers());
		mav.addObject("test", test);
		return mav;
	}

	@RequestMapping(value = "/lmsRemoveSection", method = RequestMethod.GET)
	public ModelAndView removeSection(@RequestParam String sectionName, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		Test test = (Test) request.getSession().getAttribute("test");
		List<SectionDto> sectionDtos = test.getSectionDtos();
		if (sectionDtos.size() == 1) {
			mav.addObject("sectionDto", sectionDtos.get(0));
			SectionDto dto = (SectionDto) request.getSession().getAttribute("sectionDto");
			mav.addObject("test", test);
			mav.addObject("sectionDto", dto);
			mav.addObject("message", "You can not have a Test with no sections. This section can not be deleted ");// later
			// put
			// it
			// as
			// label
			mav.addObject("msgtype", "Information");
			mav.addObject("icon", "error");
			return mav;
		}

		for (SectionDto dto : sectionDtos) {
			dto.setCurrent(false);
			if (dto.getSectionName().equals(sectionName)) {

				if (dto.getSectionId() != null) {
					Section section = sectionService.getSectionById(dto.getSectionId());
					sectionService.removeSection(section);
				}
				mav.addObject("message", "Section - " + dto.getSectionName() + " deleted");// later
				// put
				// it
				// as
				// label
				mav.addObject("msgtype", "Information");
				mav.addObject("icon", "success");
			}
		}

		boolean res = sectionDtos.remove(new SectionDto(sectionName));
		int count = 0;
		for (SectionDto dto : sectionDtos) {
			if (count == 0) {
				dto.setCurrent(true);
				mav.addObject("sectionDto", dto);
				request.getSession().setAttribute("sectionDTO", dto);
				List<Question> qs = questionService.findQuestions(user.getCompanyId());
//				List<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId());
				mav.addObject("qs", process(qs, dto));

			}
			count++;

		}

		mav.addObject("test", test);
		return mav;
	}

	@RequestMapping(value = "/lmsAddteststep3", method = RequestMethod.GET)
	public ModelAndView addteststep3(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String message) {
		ModelAndView mav = null;
		mav = new ModelAndView("lms_add_test_step3_new");
		Test test = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test);
		if (message != null) {
			mav.addObject("message", "Invalid Start or End Date selected for the Test Link");// later

			mav.addObject("msgtype", "Wrong Data");
			mav.addObject("icon", "error");
		}
		return mav;
	}

	@RequestMapping(value = "/lmsAddQuestionToSection", method = RequestMethod.GET)
	public ModelAndView addQuestionsToSection(@RequestParam String sectionName, @RequestParam String questionId,
			HttpServletRequest request, HttpServletResponse response, @ModelAttribute("test") Test test) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		Question q = questionService.findById(Long.valueOf(questionId));
		sectionDto.getQuestions().add(q);
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
//		List<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId());
		mav.addObject("qs", process(qs, sectionDto));

		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("sectionDto", sectionDto);
		Test test2 = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test2);
		return mav;
	}

//	@RequestMapping(value = "/lmsAddQuestionsByCategoryToSectionAjax", method = RequestMethod.GET)
//	public ModelAndView addQuestionsByCategoryToSectionAjax(@RequestParam(required = true) String qualifier1,
//			@RequestParam(required = false) String qualifier2, @RequestParam(required = false) String qualifier3,
//			@RequestParam(required = false) String qualifier4, @RequestParam(required = false) String qualifier5,
//			HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		List<Question> questions = new ArrayList<>();
//		if (qualifier2 == null) {
//			questions = questionService.findQuestionsByQualifier1(user.getCompanyId(), qualifier1);
//		} else if (qualifier3 == null) {
//			questions = questionService.findQuestionsByQualifier2(user.getCompanyId(), qualifier1, qualifier2);
//		} else if (qualifier4 == null) {
//			questions = questionService.findQuestionsByQualifier3(user.getCompanyId(), qualifier1, qualifier2, qualifier3);
//		} else if (qualifier5 == null) {
//			questions = questionService.findQuestionsByQualifier4(user.getCompanyId(), qualifier1, qualifier2, qualifier3,
//					qualifier4);
//		} else if (qualifier5 != null) {
//			questions = questionService.findQuestionsByQualifier5(user.getCompanyId(), qualifier1, qualifier2, qualifier3, qualifier4,
//					qualifier5);
//
//		}
//
//		for (Question question : questions) {
//			sectionDto.getQuestions().add(question);
//		}
//		sectionDto.setNoOfQuestions(sectionDto.getQuestions().size());
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		mav.addObject("sectionDto", sectionDto);
//		Test test2 = (Test) request.getSession().getAttribute("test");
//		mav.addObject("test", test2);
//		return mav;
//	}

//	@RequestMapping(value = "/lmsRemoveQuestionsByCategoryToSectionAjax", method = RequestMethod.GET)
//	public ModelAndView removeQuestionsByCategoryToSectionAjax(@RequestParam(required = true) String qualifier1,
//			@RequestParam(required = false) String qualifier2, @RequestParam(required = false) String qualifier3,
//			@RequestParam(required = false) String qualifier4, @RequestParam(required = false) String qualifier5,
//			HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		List<Question> questions = new ArrayList<>();
//		if (qualifier2 == null) {
//			questions = questionService.findQuestionsByQualifier1(user.getCompanyId(), qualifier1);
//		} else if (qualifier3 == null) {
//			questions = questionService.findQuestionsByQualifier2(user.getCompanyId(), qualifier1, qualifier2);
//		} else if (qualifier4 == null) {
//			questions = questionService.findQuestionsByQualifier3(user.getCompanyId(), qualifier1, qualifier2, qualifier3);
//		} else if (qualifier5 == null) {
//			questions = questionService.findQuestionsByQualifier4(user.getCompanyId(), qualifier1, qualifier2, qualifier3,
//					qualifier4);
//		} else if (qualifier5 != null) {
//			questions = questionService.findQuestionsByQualifier5(user.getCompanyId(), qualifier1, qualifier2, qualifier3, qualifier4,
//					qualifier5);
//
//		}
//
//		for (Question question : questions) {
//			sectionDto.getQuestions().remove(question);
//		}
//
//		sectionDto.setNoOfQuestions(sectionDto.getQuestions().size());
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		mav.addObject("sectionDto", sectionDto);
//		Test test2 = (Test) request.getSession().getAttribute("test");
//		mav.addObject("test", test2);
//		return mav;
//	}

	@RequestMapping(value = "/lmsRemoveAllQuestions", method = RequestMethod.GET)
	public ModelAndView removeAllQuestions(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("test") Test test) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2_new3");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		sectionDto.getQuestions().clear();
		List<Question> qs = questionService.findQuestions(user.getCompanyId());
//		List<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId());

		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("sectionDto", sectionDto);
		Test test2 = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test2);
		return mav;
	}

	@RequestMapping(value = "/lmsRemoveQuestionToSection", method = RequestMethod.GET)
	public ModelAndView removeQuestionToSection(@RequestParam String sectionName, @RequestParam String questionId,
			HttpServletRequest request, HttpServletResponse response, @ModelAttribute("test") Test test) {
		ModelAndView mav = new ModelAndView("add_test_step2");
		User user = (User) request.getSession().getAttribute("user");
		mav.addObject("user", user);
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		Question delete = new Question();
		delete.setId(Long.valueOf(questionId));
		sectionDto.getQuestions().remove(delete);
		// List<Question> qs = questionService.findQuestions(user.getCompanyId());
		List<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId());
		mav.addObject("qs", process(questions, sectionDto));

		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("sectionDto", sectionDto);
		Test test2 = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test2);
		return mav;
	}

//	@RequestMapping(value = "/lmsSearchQByQ1", method = RequestMethod.GET)
//	public ModelAndView searchQByQualifier1(@RequestParam String qualifier1, HttpServletRequest request, HttpServletResponse response) {
//		String referer = request.getHeader("Referer");
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//
//		Test test = (Test) request.getSession().getAttribute("test");
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		List<Question> questions = questionService.findQuestionsByQualifier1(user.getCompanyId(), qualifier1);
//		mav.addObject("user", user);
//		mav.addObject("sectionDto", sectionDto);
//		mav.addObject("test", test);
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		return mav;
//	}

//	@RequestMapping(value = "/lmsSearchQByQ1And2", method = RequestMethod.GET)
//	public ModelAndView searchQByQualifier1And2(@RequestParam String qualifier1, @RequestParam String qualifier2,
//			HttpServletRequest request, HttpServletResponse response) {
//		String referer = request.getHeader("Referer");
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//		List<Question> questions = questionService.findQuestionsByQualifier2(user.getCompanyId(), qualifier1, qualifier2);
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		Test test = (Test) request.getSession().getAttribute("test");
//		mav.addObject("user", user);
//		mav.addObject("sectionDto", sectionDto);
//		mav.addObject("test", test);
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		return mav;
//	}

//	@RequestMapping(value = "/lmsSearchQByQ1And2And3", method = RequestMethod.GET)
//	public ModelAndView searchQByQualifier1And2And3(@RequestParam String qualifier1, @RequestParam String qualifier2,
//			@RequestParam String qualifier3, HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//		List<Question> questions = questionService.findQuestionsByQualifier3(user.getCompanyId(), qualifier1, qualifier2, qualifier3);
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		Test test = (Test) request.getSession().getAttribute("test");
//		mav.addObject("user", user);
//		mav.addObject("sectionDto", sectionDto);
//		mav.addObject("test", test);
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		return mav;
//	}

//	@RequestMapping(value = "/lmsSearchQByQ1And2And3And4", method = RequestMethod.GET)
//	public ModelAndView searchQByQualifier1And2And3And4(@RequestParam String qualifier1, @RequestParam String qualifier2,
//			@RequestParam String qualifier3, @RequestParam String qualifier4, HttpServletRequest request,
//			HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//		List<Question> questions = questionService.findQuestionsByQualifier4(user.getCompanyId(), qualifier1, qualifier2, qualifier3,
//				qualifier4);
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		Test test = (Test) request.getSession().getAttribute("test");
//		mav.addObject("user", user);
//		mav.addObject("sectionDto", sectionDto);
//		mav.addObject("test", test);
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		return mav;
//	}

//	@RequestMapping(value = "/lmsSearchQByQ1And2And3And4And5", method = RequestMethod.GET)
//	public ModelAndView searchQByQualifier1And2And3And4And5(@RequestParam String qualifier1, @RequestParam String qualifier2,
//			@RequestParam String qualifier3, @RequestParam String qualifier4, @RequestParam String qualifier5,
//			HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("add_test_step2_new3");
//		User user = (User) request.getSession().getAttribute("user");
//		List<Question> questions = questionService.findQuestionsByQualifier5(user.getCompanyId(), qualifier1, qualifier2, qualifier3,
//				qualifier4, qualifier5);
//		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
//		Test test = (Test) request.getSession().getAttribute("test");
//		mav.addObject("user", user);
//		mav.addObject("sectionDto", sectionDto);
//		mav.addObject("test", test);
//		mav.addObject("qs", process(questions, sectionDto));
//		mav.addObject("levels", DifficultyLevel.values());
//		mav.addObject("types", QuestionType.values());
//		mav.addObject("languages", ProgrammingLanguage.values());
//		return mav;
//	}

	@RequestMapping(value = "/lmsSearchQs", method = RequestMethod.GET)
	public ModelAndView searchQuestions(@RequestParam String searchText, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_add_test_step2_new3");
		User user = (User) request.getSession().getAttribute("user");
		List<Question> questions = questionService.searchQuestions(user.getCompanyId(), searchText);
		Test test = (Test) request.getSession().getAttribute("test");
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");
		mav.addObject("user", user);
		mav.addObject("sectionDto", sectionDto);
		mav.addObject("test", test);
		mav.addObject("qs", process(questions, sectionDto));
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		return mav;
	}

	@RequestMapping(value = "/lmsDuplicateTest", method = RequestMethod.GET)
	public ModelAndView duplicateTest(@RequestParam String existing_name, @RequestParam String newTest,
			@RequestParam String newQual1, @RequestParam String newQual2, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lms_test_list");
		User user = (User) request.getSession().getAttribute("user");
		//

		String testToDuplicate = request.getParameter("existing_name");
//		String newTest = request.getParameter("newTest");
//		String newQual1 = request.getParameter("newQual1");
//		String newQual2 = request.getParameter("newQual2");
		Test old = testService.findbyTest(testToDuplicate, user.getCompanyId());
		Test exist = testService.findbyTest(newTest, user.getCompanyId());
		if (exist != null) {
			mav.addObject("message", "Test with a name- " + newTest + " exists. Please use a different name!");// later
			// put
			// it
			// as
			// label
			mav.addObject("msgtype", "Information");
			mav.addObject("icon", "error");
			List<Test> listTest = testService.findByCompanyId(user.getCompanyId());
			List<Test> tests2 = new ArrayList<>();
			int x = 1;
			for (Test test : listTest) {
				System.out.println(test.getCategory() + " : " + test.getcDate() + " : " + test.getuDate() + " : "
						+ test.getPublicUrl());
				Test test2 = new Test();
				test2.setSrNo(x);
				test2.setTestName(test.getTestName());
				test2.setCategory(test.getCategory());
				test2.setTestTimeInMinutes(test.getTestTimeInMinutes());
				test2.setPassPercent(test.getPassPercent());
				test2.setcDate(test.getcDate());
				test2.setuDate(test.getuDate());
				test2.setId(test.getId());
				tests2.add(test2);
				x++;
			}
			Gson gson = new Gson();
			String listTests = gson.toJson(tests2);
			mav.addObject("listTests", listTests);
			return mav;
		}
		Test newTestObj = new Test();
		Mapper beanmapper = new DozerBeanMapper();
		beanmapper.map(old, newTestObj);
		newTestObj.setId(null);
		newTestObj.setTestName(newTest);
		newTestObj.setQualifier1(newQual1);
		newTestObj.setQualifier2(newQual2);
		/// dynamic
		testService.saveOrUpdate(newTestObj);

		List<Section> sections = sectionService.getSectionsForTest(testToDuplicate, user.getCompanyId());
		for (Section sec : sections) {
			String sectionName = sec.getSectionName();
			Section newNection = new Section();
			beanmapper.map(sec, newNection);
			newNection.setId(null);
			newNection.setTestName(newTest);
			sectionService.createSection(newNection);

			List<QuestionMapper> questionMappers = sectionService.getQuestionsForSection(testToDuplicate, sectionName,
					user.getCompanyId());
			for (QuestionMapper mapper : questionMappers) {
				Question q = mapper.getQuestion();
				sectionService.addQuestionToSection(q, newNection, 1);
			}
		}
		mav.addObject("message",
				"Congratulations. Test with a name- " + newTest + " duplicated from " + old.getTestName());// later put
		// it as
		// label
		mav.addObject("msgtype", "Success");
		mav.addObject("icon", "success");
		List<Test> listTest = testService.findByCompanyId(user.getCompanyId());
		List<Test> tests2 = new ArrayList<>();
		int x = 1;
		for (Test test : listTest) {
			System.out.println(test.getCategory() + " : " + test.getcDate() + " : " + test.getuDate() + " : "
					+ test.getPublicUrl());
			Test test2 = new Test();
			test2.setSrNo(x);
			test2.setTestName(test.getTestName());
			test2.setCategory(test.getCategory());
			test2.setTestTimeInMinutes(test.getTestTimeInMinutes());
			test2.setPassPercent(test.getPassPercent());
			test2.setcDate(test.getcDate());
			test2.setuDate(test.getuDate());
			test2.setId(test.getId());
			tests2.add(test2);
			x++;
		}
		Gson gson = new Gson();
		String listTests = gson.toJson(tests2);
		mav.addObject("listTests", listTests);
		return mav;

	}

	private List<Question> process(List<Question> questions, SectionDto sectionDto) {
		for (Question question : questions) {
			if (sectionDto.getQuestions().contains(question)) {
				question.setSelected(true);
			}
		}

		return questions;
	}

	private boolean checkMultipleSectionWithSameNames(String sectionName, HttpServletRequest request) {
		Test test = (Test) request.getSession().getAttribute("test");
		int count = 0;
		for (SectionDto dto : test.getSectionDtos()) {
			if (dto.getSectionName().equals(sectionName)) {
				count++;
			}
		}

		if (count > 1) {
			return true;
		}
		return false;
	}

}
