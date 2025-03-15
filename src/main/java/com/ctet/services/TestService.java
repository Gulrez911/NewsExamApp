package com.ctet.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import com.ctet.data.Test;

public interface TestService {

	public Test findbyTest(String testName, String companyId);

	public void saveOrUpdate(Test test);

	public Integer computeTestTotalMarksAndSave(Test test);

	List<Test> findByCompanyId(@Param("companyId") String companyId);

	public Page<Test> findByCompanyId(String companyId, Integer pageNumber);

	public List<Test> populateWithPublicUrl(List<Test> tests);

	public Test findTestById(Long id);

	public Test findTestById(Long testId, String companyId);

	public void removeTest(String companyId, Long testId);

	public List<Test> getTestNames(String companyId);
}
