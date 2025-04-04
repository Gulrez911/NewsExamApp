package com.ctet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.Company;
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("SELECT c FROM Company c WHERE c.companyName=:companyName and c.companyId=:companyId")
	Company findByPrimaryKey(@Param("companyName") String companyName, @Param("companyId") String companyId);

	@Query("SELECT c FROM Company c WHERE c.companyName=:companyName")
	Company findByCompanyNameIgnoreCase(@Param("companyName") String companyName);

	@Query("SELECT c FROM Company c WHERE c.companyId=:companyId")
	Company findByCompanyId(@Param("companyId") String companyId);
}
