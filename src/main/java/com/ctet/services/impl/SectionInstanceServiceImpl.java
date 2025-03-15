package com.ctet.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.Exceptions.AssessmentGenericException;
import com.ctet.data.QuestionMapperInstance;
import com.ctet.data.SectionInstance;
import com.ctet.repositories.QuestionMapperInstanceRepository;
import com.ctet.repositories.SectionInstanceRepository;
import com.ctet.services.QuestionMapperInstanceService;
import com.ctet.services.SectionInstanceService;

@Service
@Transactional
public class SectionInstanceServiceImpl implements SectionInstanceService {

	@Autowired
	SectionInstanceRepository sectionInstanceRepository;
	@Autowired
	QuestionMapperInstanceRepository questionMapperInstanceRepository;
	@Autowired
	QuestionMapperInstanceService mapperInstanceService;
	@Autowired
	EntityManager entityManager;

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	private void validateMandatoryFields(QuestionMapperInstance questionMapperInstance) {
		Set<ConstraintViolation<QuestionMapperInstance>> violations = validator.validate(questionMapperInstance);
		if (violations.size() > 0) {
			throw new AssessmentGenericException("NOT_SUFFICIENT_PARAMS");
		}

	}

	@Override
	public QuestionMapperInstance saveOrUpdateAnswer(QuestionMapperInstance questionMapperInstance) {
		// TODO Auto-generated method stub
		QuestionMapperInstance questionMapperInstance2 = null;
		String qText = questionMapperInstance.getQuestionText();
		if (qText == null) {
			qText = questionMapperInstance.getQuestionMapper() == null ? qText : questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText();
		}
		questionMapperInstance2 = mapperInstanceService.removeDublicateAndGetInstance(qText, questionMapperInstance.getTestName(),
				questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
		if (questionMapperInstance2 != null) {
			// update answer
			questionMapperInstance2.setUserChoices(questionMapperInstance.getUserChoices());
			String type = questionMapperInstance2.getQuestionMapper().getQuestion().getType();

			questionMapperInstance2.setUpdateDate(new Date());
			checkAnswer(questionMapperInstance2);
			return questionMapperInstanceRepository.save(questionMapperInstance2);
		} else {
			// new answer
			questionMapperInstance.setCreateDate(new Date());
			checkAnswer(questionMapperInstance);
			questionMapperInstance.setQuestionText(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
			return questionMapperInstanceRepository.save(questionMapperInstance);
		}
	}

	@Override
	public List<SectionInstance> getSectionInstances(String sectionName, String companyId, String user) {
		// TODO Auto-generated method stub
		return sectionInstanceRepository.findSectionForUser(sectionName, user, companyId);
	}

	@Override
	public void addOnlyIfAnswersNotPresent(SectionInstance sectionInstance, List<QuestionMapperInstance> instances) {
		// TODO Auto-generated method stub
		List<SectionInstance> pastInstances = getSectionInstances(sectionInstance.getSectionName(), sectionInstance.getCompanyId(), sectionInstance.getUser());

		int timeYet = 0;
		for (SectionInstance ins : pastInstances) {
			Long startTime = ins.getStartTime();
			Long endTime = ins.getEndTime();
			int mins = (int) ((endTime - startTime) / (1000 * 60));
			timeYet += mins;
		}

		sectionInstance.setTotalTimeTaken(timeYet);
		sectionInstanceRepository.save(sectionInstance);

		List<QuestionMapperInstance> createAnswers = new ArrayList<QuestionMapperInstance>();
		for (QuestionMapperInstance questionMapperInstance : instances) {
			validateMandatoryFields(questionMapperInstance);
			QuestionMapperInstance questionMapperInstance2 = null;
			List<QuestionMapperInstance> answers = questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUserSet(
					questionMapperInstance.getQuestionText(), questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(),
					questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
			if (answers == null || answers.size() == 0) {
				questionMapperInstance.setCreateDate(new Date());
				checkAnswer(questionMapperInstance);
				createAnswers.add(questionMapperInstance);
			}
		}
		saveAnswersInBatch(createAnswers);

	}

	public void saveAnswersInBatch(List<QuestionMapperInstance> answers) {
		for (int i = 0; i < answers.size(); i++) {
			if (i > 0 && i % 20 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
			QuestionMapperInstance answer = answers.get(i);
			entityManager.persist(answer);
		}
	}

	private void checkAnswer(QuestionMapperInstance instance) {

		if (instance.getAnswered()) {
			String rightChoices = instance.getQuestionMapper().getQuestion().getRightChoices();
			String rt[] = rightChoices.split("-");
			String userChoices[] = instance.getUserChoices().split("-");
			instance.setCorrect(Arrays.equals(rt, userChoices));
		}
	}
}
