package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.QuestionMapperInstance;
@Repository
public interface QuestionMapperInstanceRepository extends JpaRepository<QuestionMapperInstance, Long> {

	@Query("SELECT q FROM QuestionMapperInstance q WHERE q.sectionName=:sectionName and q.testName=:testName and q.companyId=:companyId GROUP BY q.questionMapper.id")
	List<QuestionMapperInstance> findQuestionMapperInstancesForTestAndSection(@Param("sectionName") String sectionName, @Param("testName") String testName,
			@Param("companyId") String companyId);

	@Query("SELECT q FROM QuestionMapperInstance q WHERE q.questionText=:questionText and q.testName=:testName and q.sectionName=:sectionName and q.user=:user and q.companyId=:companyId")
	List<QuestionMapperInstance> findUniqueQuestionMapperInstanceForUserSet(@Param("questionText") String questionText, @Param("testName") String testName,
			@Param("sectionName") String sectionName, @Param("user") String user, @Param("companyId") String companyId);

	@Query("SELECT q FROM QuestionMapperInstance q WHERE q.questionText LIKE %:questionText% and q.testName=:testName and q.sectionName=:sectionName and q.user=:user and q.companyId=:companyId")
	List<QuestionMapperInstance> searcUniqueQuestionMapperInstanceForUserSet(@Param("questionText") String questionText, @Param("testName") String testName,
			@Param("sectionName") String sectionName, @Param("user") String user, @Param("companyId") String companyId);

	@Query("SELECT q FROM QuestionMapperInstance q WHERE q.questionText=:questionText and q.testName=:testName and q.sectionName=:sectionName and q.user=:user and q.companyId=:companyId")
	List<QuestionMapperInstance> findDuplicateQuestionMapperInstanceForUser(@Param("questionText") String questionText, @Param("testName") String testName,
			@Param("sectionName") String sectionName, @Param("user") String user, @Param("companyId") String companyId);

	@Query("SELECT q FROM QuestionMapperInstance q WHERE  q.testName=:testName and q.user=:user and q.companyId=:companyId GROUP BY q.questionMapper.id")
	List<QuestionMapperInstance> findQuestionMapperInstancesForUserForTest(@Param("testName") String testName, @Param("user") String user,
			@Param("companyId") String companyId);
}
