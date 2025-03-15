package com.ctet.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.Question;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query(value = "SELECT q FROM Question q ", countQuery = "SELECT COUNT(*) FROM Question q ")
	public Page<Question> findQuestionsByPageNumber(Pageable pageable);

	@Query("SELECT q FROM Question q WHERE q.questionText=:questionText")
	Question findByPrimaryKey(@Param("questionText") String questionText);

	@Query(value = "SELECT q FROM Question q WHERE q.companyId=:companyId  GROUP BY q.qualifier1")
	public Page<Question> getAllLevel1Questions(@Param("companyId") String companyId, Pageable pageable);

	@Query(value = "SELECT q FROM Question q WHERE q.companyId=:companyId")
	public Page<Question> findByCompanyId(@Param("companyId") String companyId, Pageable pageNumber);

	@Query(value = "SELECT q FROM Question q WHERE q.companyId=:companyId")
	public List<Question> findQuestions(@Param("companyId") String companyId);

	@Query("SELECT q FROM Question q WHERE q.questionText=:questionText and q.companyId=:companyId")
	Question findByPrimaryKey(@Param("questionText") String questionText, @Param("companyId") String companyId);

	@Query(value = "SELECT q FROM Question q WHERE q.companyId=:companyId and q.questionText LIKE %:searchText%")
	public List<Question> searchQuestions(@Param("companyId") String companyId, @Param("searchText") String searchText);

	@Query(value = "SELECT q FROM Question q WHERE q.companyId=:companyId and q.questionText LIKE %:searchText%", countQuery = "SELECT COUNT(*) FROM Question q WHERE q.companyId=:companyId and q.questionText LIKE %:searchText%")
	public Page<Question> searchQuestions(@Param("companyId") String companyId, @Param("searchText") String searchText, Pageable pageable);

}
