package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email=:email")
	User findByPrimaryKey(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.email=:email")
	List<User> findByPrimaryKeyFromList(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.companyId=:companyId")
	List<User> findByPrimaryKeyFromList(@Param("email") String email, @Param("companyId") String companyId);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.password=:password")
	User findByPrimaryKeyAndPassword(@Param("email") String email, @Param("password") String password);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.password=:password and u.companyId=:companyId")
	User findByPrimaryKeyAndPassword(@Param("email") String email, @Param("password") String password,
			@Param("companyId") String companyId);

	@Query("SELECT u FROM User u WHERE u.companyId=:companyId")
	List<User> findByCompany(@Param("companyId") String companyId);

	@Query(value = "SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));", nativeQuery = true)
	void sql_mode();
}