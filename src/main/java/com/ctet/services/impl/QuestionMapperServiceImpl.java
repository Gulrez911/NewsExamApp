package com.ctet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.data.QuestionMapper;
import com.ctet.repositories.QuestionMapperRepository;
import com.ctet.services.QuestionMapperService;

@Service
@Transactional
public class QuestionMapperServiceImpl implements QuestionMapperService {

	@Autowired
	QuestionMapperRepository questionMapperRepo;

	@Override
	public List<QuestionMapper> getQuestionsForSection(String testName, String sectionName, String companyId) {
		return questionMapperRepo.getQuestionsForSection(testName, sectionName, companyId);
	}

}