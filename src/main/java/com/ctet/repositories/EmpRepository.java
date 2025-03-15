package com.ctet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctet.entity.Employee;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Long> {

//	Employee findByEmail(String username);

	Optional<Employee> findByEmail(String email);

	Boolean existsByEmail(String email);
}