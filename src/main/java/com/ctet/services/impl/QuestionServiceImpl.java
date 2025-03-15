package com.ctet.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.data.DifficultyLevel;
import com.ctet.data.Question;
import com.ctet.data.QuestionMapper;
import com.ctet.data.QuestionType;
import com.ctet.repositories.QuestionMapperRepository;
import com.ctet.repositories.QuestionRepository;
import com.ctet.services.QuestionService;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	QuestionMapperRepository questionMapperRepository;

	@Override
	public void createQuestion(Question question) {
		Question q = questionRepository.findByPrimaryKey(question.getQuestionText());
		if (q != null) {
			Mapper mapper = new DozerBeanMapper();
			question.setId(q.getId());
			question.setCreateDate(q.getCreateDate());
			q.setUpdateDate(new Date());
			mapper.map(question, q);
			questionRepository.save(q);
		} else {
			question.setCreateDate(new Date());
			questionRepository.save(question);
		}
	}

	@Override
	public Page<Question> findQuestionsByQualifier1AndPage(String companyId, String qualifier1, Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findQuestionsByQualifier1(String companyId, String qualifier1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> findQuestionsByQualifier2AndPage(String companyId, String qualifier1, String qualifier2, Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findQuestionsByQualifier2(String companyId, String qualifier1, String qualifier2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> findQuestionsByQualifier3AndPage(String companyId, String qualifier1, String qualifier2, String qualifier3, Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findQuestionsByQualifier3(String companyId, String qualifier1, String qualifier2, String qualifier3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> findQuestionsByQualifier4AndPage(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findQuestionsByQualifier4(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> findQuestionsByQualifier5AndPage(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findQuestionsByQualifier5(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> uploadQuestionsFromExcelDoc(FileInputStream fileInputStream, File mappingObjectFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findQuestions(String companyId) {

		return questionRepository.findQuestions(companyId);
	}

	@Override
	public Page<Question> searchQuestions(String companyId, String searchText, Integer pageNumber) {
	 
		return questionRepository.searchQuestions(companyId, searchText, PageRequest.of(pageNumber, 10));
		
	}

	@Override
	public List<Question> searchQuestions(String companyId, String searchText) {
		// TODO Auto-generated method stub
		return questionRepository.searchQuestions(companyId, searchText);
	}

	@Override
	public Question findById(Long id) {

		return questionRepository.findById(id).get();
	}

	@Override
	public Page<Question> findQuestionsByPage(Integer pageNumber) {
		// TODO Auto-generated method stub
		return questionRepository.findQuestionsByPageNumber(PageRequest.of(pageNumber, 10));
	}

	@Override
	public List<Question> getAllLevel1Questions(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> getAllLevel1Questions(String companyId, Integer pageNumber) {

		return questionRepository.getAllLevel1Questions(companyId, PageRequest.of(pageNumber, 10));

	}

	@Override
	public boolean canDeleteQuestion(Long qid) {
		List<QuestionMapper> mappers = questionMapperRepository.findByQuestion_id(qid);
		if (mappers.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void updateQuestion(Question q) {
		Question q2 = questionRepository.findById(q.getId()).get();
		Mapper mapper = new DozerBeanMapper();
		mapper.map(q, q2);
		q2.setConstrnt(q.getConstrnt());
		q2.setInstructionsIfAny(q.getInstructionsIfAny());
		q2.setUpdateDate(new Date());
		questionRepository.save(q2);
 
	}

	@Override
	public void removeQuestion(Long id) {
		questionRepository.deleteById(id);
	}

	@Override
	public List<Question> getRandomQuestions(String companyId, String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5,
			DifficultyLevel level, QuestionType type, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> findByCompanyId(String companyId, Integer pageNumber) {
		return questionRepository.findByCompanyId(companyId, PageRequest.of(pageNumber, 10));

	}
}
