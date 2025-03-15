package com.ctet.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.Test;
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

	@Query("SELECT t FROM Test t WHERE t.testName=:testName and t.companyId=:companyId")
	Test findByPrimaryKey(@Param("testName") String testName, @Param("companyId") String companyId);

	@Query("SELECT t FROM Test t WHERE  t.companyId=:companyId")
	List<Test> findByCompanyId(@Param("companyId") String companyId);

	@Query(value = "SELECT t FROM Test t WHERE  t.companyId=:companyId", countQuery = "SELECT COUNT(*) FROM Test t WHERE  t.companyId=:companyId")
	Page<Test> findByCompanyId(@Param("companyId") String companyId, Pageable pageable);

	@Query("SELECT t FROM Test t WHERE t.id=:testId and t.companyId=:companyId")
	Test findTestById(@Param("testId") Long TestId, @Param("companyId") String companyId);

	@Query("SELECT t FROM Test t WHERE t.companyId=:companyId and t.testName IS NOT NULL ORDER BY t.testName")
	List<Test> getTestNames(@Param("companyId") String companyId);
}
