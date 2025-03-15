package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.Category;
import com.ctet.data.Exam;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(value = "SELECT c FROM Category c WHERE c.companyId=:companyId")
	public List<Category> getListCategory(@Param("companyId") String companyId);

	@Query(value = "SELECT c FROM Category c WHERE c.id=:id and c.companyId=:companyId")
	public Category getExamsByCategoryId(@Param("id") Long id, @Param("companyId") String companyId);

	@Query(value = "SELECT c FROM Category c   join Exam e  on c.id=e.id")
	public List<Category> findCategoryByExam(@Param("id") Long id);

}