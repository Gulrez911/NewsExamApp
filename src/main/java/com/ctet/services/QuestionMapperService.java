package com.ctet.services;

import java.util.List;

import com.ctet.data.QuestionMapper;

public interface QuestionMapperService {

	public List<QuestionMapper> getQuestionsForSection(String testName, String sectionName, String companyId);

}
