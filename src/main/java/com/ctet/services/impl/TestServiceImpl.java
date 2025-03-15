package com.ctet.services.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.Exceptions.AssessmentGenericException;
import com.ctet.common.PropertyConfig;
import com.ctet.data.Section;
import com.ctet.data.Test;
import com.ctet.repositories.QuestionMapperRepository;
import com.ctet.repositories.TestRepository;
import com.ctet.services.SectionService;
import com.ctet.services.TestService;

@Service
@Transactional
public class TestServiceImpl implements TestService {

	Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

	@Autowired
	TestRepository testRepository;

	@Autowired
	PropertyConfig propertyConfig;
	@Autowired
	SectionService sectionService;

	@Autowired
	QuestionMapperRepository questionMapperRepository;

	@Override
	public void removeTest(String companyId, Long testId) {
		// TODO Auto-generated method stub
		Test test = findTestById(testId);
		List<Section> sections = sectionService.getSectionsForTest(test.getTestName(), test.getCompanyId());
		for (Section section : sections) {
			sectionService.removeSection(section);
		}
		testRepository.deleteById(testId);
	}

	@Override
	public Page<Test> findByCompanyId(String companyId, Integer pageNumber) {
		// TODO Auto-generated method stub
		return testRepository.findByCompanyId(companyId, PageRequest.of(pageNumber, 5));
	}

	@Override
	public Test findbyTest(String testName, String companyId) {
		return populate(testRepository.findByPrimaryKey(testName, companyId));
	}

	public List<Test> populateWithPublicUrl(List<Test> tests) {
		for (Test test : tests) {
			test = populate(test);
		}
		return tests;
	}

	public Test populate(Test test) {
		if (test == null) {
			return null;
		}
		String url = getPublicUrlForTest(test.getId(), test.getCompanyId());
		test.setPublicUrl(url);
		return test;
	}

	public String getPublicUrlForTest(Long testId, String companyId) {
		// String after =
		// "&testId="+URLEncoder.encode(Base64.getEncoder().encodeToString(testId.toString().getBytes())+"&companyId="+URLEncoder.encode(Base64.getEncoder().encodeToString(companyId.getBytes())));
		String after = "&testId=" + URLEncoder.encode(testId.toString()) + "&companyId=" + URLEncoder.encode(companyId);
		String url = propertyConfig.getBaseUrl() + "publicTest?" + after;
		return url;
	}

	@Override
	public void saveOrUpdate(Test test) {
		// Test test2 = findbyTest(test.getTestName(), test.getCompanyId());
		Test test2 = null;
		if (test.getId() != null) {
			test2 = testRepository.getOne(test.getId());

		}
		if (test2 == null) {
			test.setCreateDate(new Date());
			testRepository.save(test);
		} else {
			test.setId(test2.getId());
			test.setCreateDate(test2.getCreateDate());
			test.setUpdateDate(new Date());
			Mapper mapper = new DozerBeanMapper();
			mapper.map(test, test2);
			testRepository.save(test2);

		}
	}

	@Override
	public Integer computeTestTotalMarksAndSave(Test test) {
		Test test2 = null;
		if (test.getId() != null) {
			test2 = testRepository.getOne(test.getId());

		} else {
			throw new AssessmentGenericException("NO_TEST_ID_PRESENT");
		}

		List<Section> sections = sectionService.getSectionsForTest(test.getTestName(), test.getCompanyId());
		Integer totalMarks = 0;
		for (Section section : sections) {
			Integer percent = (section.getPercentQuestionsAsked() == null || section.getPercentQuestionsAsked() == 0)
					? 100
					: section.getPercentQuestionsAsked();
			Integer noOfQuestions = questionMapperRepository.findCountQuestionMapperForTestAndSection(
					section.getSectionName().trim(), test.getTestName().trim(), test.getCompanyId().trim());
			Integer sectionMarks = (Integer) ((noOfQuestions * percent) / 100);
			totalMarks += sectionMarks;
		}
		test2.setTotalMarks(totalMarks);
		testRepository.save(test2);
		return totalMarks;
	}

	@Override
	public List<Test> findByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return populateWithPublicUrl(testRepository.findByCompanyId(companyId));
	}

	@Override
	public Test findTestById(Long id) {
		// TODO Auto-generated method stub
		return populate(testRepository.findById(id).get());
	}

	@Override
	public Test findTestById(Long testId, String companyId) {
		return populate(testRepository.findTestById(testId, companyId));
	}

	@Override
	public List<Test> getTestNames(String companyId) {
		// TODO Auto-generated method stub
		return testRepository.getTestNames(companyId);
	}
}
