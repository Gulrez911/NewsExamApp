package com.ctet.services;

import java.util.List;

import com.ctet.data.QuestionMapperInstance;
import com.ctet.data.SectionInstance;

public interface SectionInstanceService {

	public QuestionMapperInstance saveOrUpdateAnswer(QuestionMapperInstance questionMapperInstance);

	public void addOnlyIfAnswersNotPresent(SectionInstance sectionInstance, List<QuestionMapperInstance> instances);

	public List<SectionInstance> getSectionInstances(String sectionName, String companyId, String user);
}