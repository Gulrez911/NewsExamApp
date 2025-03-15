package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.Category;
import com.ctet.data.Exam;
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

	@Query(value = "SELECT e FROM Exam e WHERE e.companyId=:companyId")
	public List<Exam> getListExams(@Param("companyId") String companyId);

	@Query("SELECT e FROM Exam e WHERE e.examName=:examName and e.companyId=:companyId")
	Exam findByPrimaryKey(@Param("examName") String examName, @Param("companyId") String companyId);

	@Query(value = "SELECT e FROM Exam e WHERE e.id=:id and e.companyId=:companyId")
	public Exam getTestsByExamId(@Param("id") Long id, @Param("companyId") String companyId);
	
}