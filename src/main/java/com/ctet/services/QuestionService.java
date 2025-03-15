package com.ctet.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ctet.data.DifficultyLevel;
import com.ctet.data.Question;
import com.ctet.data.QuestionType;

public interface QuestionService {
	public void createQuestion(Question question);

	public Page<Question> findQuestionsByQualifier1AndPage(String companyId, String qualifier1, Integer pageNumber);

	public List<Question> findQuestionsByQualifier1(String companyId, String qualifier1);

	public Page<Question> findQuestionsByQualifier2AndPage(String companyId, String qualifier1, String qualifier2, Integer pageNumber);

	public List<Question> findQuestionsByQualifier2(String companyId, String qualifier1, String qualifier2);

	public Page<Question> findQuestionsByQualifier3AndPage(String companyId, String qualifier1, String qualifier2, String qualifier3, Integer pageNumber);

	public List<Question> findQuestionsByQualifier3(String companyId, String qualifier1, String qualifier2, String qualifier3);

	public Page<Question> findQuestionsByQualifier4AndPage(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4,
			Integer pageNumber);

	public List<Question> findQuestionsByQualifier4(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4);

	public Page<Question> findQuestionsByQualifier5AndPage(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5,
			Integer pageNumber);

	public List<Question> findQuestionsByQualifier5(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5);

	public List<Question> uploadQuestionsFromExcelDoc(FileInputStream fileInputStream, File mappingObjectFile);

	public List<Question> findQuestions(String companyId);

	public Page<Question> searchQuestions(String companyId, String searchText, Integer pageNumber);

	public List<Question> searchQuestions(String companyId, String searchText);

	public Question findById(Long id);

	public Page<Question> findQuestionsByPage(Integer pageNumber);

	public List<Question> getAllLevel1Questions(String companyId);

	public Page<Question> getAllLevel1Questions(String companyId, Integer pageNumber);

	public boolean canDeleteQuestion(Long qid);

	public void updateQuestion(Question q);

	public void removeQuestion(Long id);

	public List<Question> getRandomQuestions(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5,
			DifficultyLevel level, QuestionType type, Pageable pageable);

	public Page<Question> findByCompanyId(String companyId, Integer pageNumber);
}
