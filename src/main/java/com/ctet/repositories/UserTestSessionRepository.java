package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.UserTestSession;
@Repository
public interface UserTestSessionRepository extends JpaRepository<UserTestSession, Long> {

	@Query("SELECT u FROM UserTestSession u WHERE u.user=:user and u.testName=:testName and u.companyId=:companyId")
	UserTestSession findByPrimaryKey(@Param("user") String user, @Param("testName") String testName, @Param("companyId") String companyId);

	@Query("SELECT u FROM UserTestSession u WHERE u.user LIKE CONCAT(:user,'%') and u.testName=:testName and u.companyId=:companyId")
	List<UserTestSession> findByTestNamePart(@Param("user") String user, @Param("testName") String testName,
			@Param("companyId") String companyId);


}
