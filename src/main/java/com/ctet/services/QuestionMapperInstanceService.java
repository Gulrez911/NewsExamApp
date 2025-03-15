package com.ctet.services;

import java.util.List;

import com.ctet.data.QuestionMapperInstance;

public interface QuestionMapperInstanceService {

	public boolean canEditTest(String sectionName, String testName, String companyId);
	
	public QuestionMapperInstance removeDublicateAndGetInstance(String questionText, String testName,
			String sectionName, String user, String companyId);
	
	public void deleteDuplicateAnswers(List<QuestionMapperInstance> qms);
}
