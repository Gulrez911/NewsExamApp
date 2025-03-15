package com.ctet.services.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.Exceptions.AssessmentGenericException;
import com.ctet.data.QuestionMapperInstance;
import com.ctet.data.Test;
import com.ctet.data.UserTestSession;
import com.ctet.repositories.QuestionMapperInstanceRepository;
import com.ctet.repositories.UserTestSessionRepository;
import com.ctet.repositories.UserTestSessionService;
import com.ctet.services.TestService;

@Service
@Transactional
public class UserTestSessionServiceImpl implements UserTestSessionService {

	@Autowired
	UserTestSessionRepository userTestSessionRep;

	@Autowired
	TestService testService;

	@Autowired
	QuestionMapperInstanceRepository questionMapperInstanceRepository;

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	@Override
	public UserTestSession findUserTestSession(String user, String testName, String companyId) {
		// TODO Auto-generated method stub
		return userTestSessionRep.findByPrimaryKey(user, testName, companyId);
	}

	private void validateMandatoryFields(UserTestSession userTestSession) {
		Set<ConstraintViolation<UserTestSession>> violations = validator.validate(userTestSession);
		if (violations.size() > 0) {
			throw new AssessmentGenericException("NOT_SUFFICIENT_PARAMS");
		}

	}

	private Float getWeightedScoreForTest(List<QuestionMapperInstance> instances) {
		Map<Integer, List<QuestionMapperInstance>> map = new HashMap<Integer, List<QuestionMapperInstance>>();
		for (QuestionMapperInstance instance : instances) {
			Integer weight = instance.getQuestionMapper().getQuestion().getQuestionWeight();
			if (weight == null) {
				weight = 1;
			}
			if (map.get(weight) == null) {
				List<QuestionMapperInstance> list = new ArrayList<QuestionMapperInstance>();
				list.add(instance);
				map.put(weight, list);
			} else {
				map.get(weight).add(instance);
			}
		}
		Map<Integer, Float> map_weight_percentage = new HashMap<Integer, Float>();
		for (Integer weight : map.keySet()) {
			List<QuestionMapperInstance> instances2 = map.get(weight);
			Integer noOfCorrect = 0;
			for (QuestionMapperInstance instance : instances2) {
				if (instance.getCorrect()) {
					noOfCorrect++;
				}
			}
			Float percentageForWeightQs = (float) (100 * noOfCorrect / instances2.size());
			map_weight_percentage.put(weight, percentageForWeightQs);
		}
		Integer totalWeight = 0;
		Float totalScore = 0f;
		for (Integer weight : map_weight_percentage.keySet()) {
			totalWeight += weight;
			Float percentageForWeight = map_weight_percentage.get(weight);
			totalScore += percentageForWeight * weight;
		}
		Float weightedScoreForTest = totalScore / totalWeight;
		return weightedScoreForTest;
	}

	private UserTestSession calculateResults(UserTestSession userTestSession, Test test) {
		List<QuestionMapperInstance> questionMapperInstances = questionMapperInstanceRepository.findQuestionMapperInstancesForUserForTest(userTestSession.getTestName(),
				userTestSession.getUser(), userTestSession.getCompanyId());
		Integer totalMarks = 0;
		Integer totalMarksRecieved = 0;
		for (QuestionMapperInstance questionMapperInstance : questionMapperInstances) {
			totalMarks += questionMapperInstance.getQuestionMapper().getPointsToAward();
			if (questionMapperInstance.getCorrect()) {
				totalMarksRecieved += questionMapperInstance.getQuestionMapper().getPointsToAward();
			}
		}
		float per = new Float(totalMarksRecieved) / new Float(totalMarks) * 100;
		DecimalFormat df = new DecimalFormat("##.##");
		userTestSession.setPercentageMarksRecieved(Float.parseFloat(df.format(per)));
		userTestSession.setTotalMarks(totalMarks);
		userTestSession.setTotalMarksRecieved(totalMarksRecieved);
		Float weightedScore = getWeightedScoreForTest(questionMapperInstances);
		userTestSession.setWeightedScorePercentage(weightedScore);
		// if(per >= test.getPassPercent() ) {
		if (weightedScore >= test.getPassPercent()) {
			userTestSession.setPass(true);
		} else {
			userTestSession.setPass(false);
		}
		return userTestSession;
	}

	@Override
	public UserTestSession saveOrUpdate(UserTestSession userTestSession) {
		// TODO Auto-generated method stub
		validateMandatoryFields(userTestSession);
		Test test = testService.findbyTest(userTestSession.getTestName(), userTestSession.getCompanyId());

		UserTestSession userTestSession2 = findUserTestSession(userTestSession.getUser(), userTestSession.getTestName(), userTestSession.getCompanyId());
		if (userTestSession2 == null) {
			// create
			userTestSession.setTest(test);
			userTestSession.setNoOfAttempts(1);

			userTestSession = calculateResults(userTestSession, test);

			// userTestSession.setCreateDate(new Date());
			userTestSessionRep.save(userTestSession);
		}
		return userTestSession;
	}
}
