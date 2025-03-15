package com.ctet.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.data.QuestionMapperInstance;
import com.ctet.data.QuestionType;
import com.ctet.repositories.QuestionMapperInstanceRepository;
import com.ctet.services.QuestionMapperInstanceService;

@Service
@Transactional
public class QuestionMapperInstanceServiceImpl implements QuestionMapperInstanceService {

	@Autowired
	QuestionMapperInstanceRepository questionMapperInstanceRepository;

	Logger logger = LoggerFactory.getLogger(QuestionMapperInstanceServiceImpl.class);

	@Override
	public boolean canEditTest(String sectionName, String testName, String companyId) {
		// TODO Auto-generated method stub
		List<QuestionMapperInstance> list = questionMapperInstanceRepository.findQuestionMapperInstancesForTestAndSection(sectionName, testName, companyId);
		return list.size() > 0 ? false : true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public QuestionMapperInstance removeDublicateAndGetInstance(String questionText, String testName, String sectionName, String user, String companyId) {
		QuestionMapperInstance questionMapperInstance = null;
		// TODO Auto-generated method stub
		try {
			logger.info("questiontext " + questionText);
			logger.info("testName " + testName);
			logger.info("user " + user);
			logger.info("sectionName " + sectionName);
			List<QuestionMapperInstance> annswers = questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUserSet(questionText, testName, sectionName,
					user, companyId);
			// questionMapperInstance =
			// questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUser(questionText,
			// testName, sectionName, user, companyId);
			logger.info("annswers size " + annswers.size());
			if (annswers == null || annswers.size() == 0) {
				logger.info("no ans");
				if (questionText == null) {
					logger.info("**************");
					return null;
				}
				if (questionText != null && questionText.contains("\r") || questionText.contains("\t") || questionText.contains("\n")) {
					System.out.println(questionText);
					questionText = questionText.replaceAll("[" + System.lineSeparator() + "]", "%");
					// System.out.println(questionText);
					logger.info("questiontext " + questionText);
					logger.info("searcUniqueQuestionMapperInstanceForUserSet called");
					annswers = questionMapperInstanceRepository.searcUniqueQuestionMapperInstanceForUserSet(questionText, testName, sectionName, user,
							companyId);
					// System.out.println("annswers "+annswers);
					logger.info("searcUniqueQuestionMapperInstanceForUserSet called " + (annswers == null ? 0 : annswers.size()));
					if (annswers == null || annswers.size() == 0) {
						logger.info("0000 1");
						return null;
					} else if (annswers.size() > 0) {
						QuestionType type = annswers.get(0).getQuestionMapper().getQuestion().getQuestionType();
						logger.info("size returned " + annswers.size());
						if (type.getType().equalsIgnoreCase(QuestionType.FULL_STACK_JAVA.getType())
								|| type.getType().equalsIgnoreCase(QuestionType.FULLSTACK.getType())) {
							logger.info("size returned xxx " + annswers.size());
							return annswers.get((annswers.size() - 1));
						}

					}
				} else {
					logger.info("0000 2");
					return null;
				}

			}

			if (annswers.size() == 1) {
				return annswers.get(0);
			}

			if (annswers.size() > 1) {
				deleteDuplicateAnswers(annswers);
			}

		} catch (javax.persistence.NonUniqueResultException e) {
			// TODO Auto-generated catch block
			// should not come here
			e.printStackTrace();
			logger.error("should not come here duplicate anss for " + questionText + "-" + testName + "-" + sectionName + "-" + user + "-" + companyId);
			List<QuestionMapperInstance> qms = questionMapperInstanceRepository.findDuplicateQuestionMapperInstanceForUser(questionText, testName, sectionName, user,
					companyId);
			for (QuestionMapperInstance q : qms) {
				questionMapperInstanceRepository.delete(q);
			}
		}
		return questionMapperInstance;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteDuplicateAnswers(List<QuestionMapperInstance> qms) {
		for (QuestionMapperInstance q : qms) {
			questionMapperInstanceRepository.delete(q);
		}
	}
}