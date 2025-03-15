package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.News;
import com.ctet.data.Test;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

//	@Query(value = "SELECT c FROM Category c WHERE c.companyId=:companyId")
//	public List<Category> getListCategory(@Param("companyId") String companyId);
//
//	@Query(value = "SELECT c FROM Category c WHERE c.id=:id and c.companyId=:companyId")
//	public Category getExamsByCategoryId(@Param("id") Long id, @Param("companyId") String companyId);
//
//	@Query(value = "SELECT c FROM Category c   join Exam e  on c.id=e.id")
//	public List<Category> findCategoryByExam(@Param("id") Long id);

//	@Query(value = "SELECT * FROM News n WHERE  n.language=:language  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLangHi(@Param("language") String language);

	@Query(value = "SELECT * FROM News n WHERE  n.language=:language  ORDER BY n.id  DESC LIMIT 50", nativeQuery = true)
	List<News> findNewsByLang(@Param("language") String language);

	@Query("SELECT n FROM News n WHERE  n.simage=:simage or n.sheadline=:sheadline")
	List<News> findTestBySimage(@Param("simage") String simage, @Param("sheadline") String sheadline);

	@Query(value = "SELECT * FROM News n WHERE  n.category=:category ORDER BY n.id  DESC LIMIT 100", nativeQuery = true)
	List<News> getCategoryNews(@Param("category") String category);

	@Query(value = "SELECT * FROM News n ORDER BY n.id  DESC LIMIT 100", nativeQuery = true)
	public List<News> findAllByOrderByIdDesc();

	@Query(value = "SELECT * FROM News n ORDER BY n.id  DESC LIMIT 100", nativeQuery = true)
	public List<News> findAllByOrderByIdDescLang();

	@Query(value = "SELECT * FROM News n ORDER BY n.id  DESC LIMIT 50", nativeQuery = true)
	public List<News> findAllByOrderByIdDescNoti();

	@Query(value = "SELECT * FROM News n WHERE  (n.language=:language1 or n.language=:language2 or n.language=:language3 or n.language=:language4 or n.language=:language5 or n.language=:language6 or n.language=:language7 or n.language=:language8 or n.language=:language9 or n.language=:language10 or n.language=:language11 or n.language=:language12 or n.language=:language13 or n.language=:language14) and n.category=:category  ORDER BY n.id  DESC LIMIT 1000", nativeQuery = true)
	List<News> findNewsByFilter(@Param("language1") String language1, @Param("language2") String language2,
			@Param("language3") String language3, @Param("language4") String language4,
			@Param("language5") String language5, @Param("language6") String language6,
			@Param("language7") String language7, @Param("language8") String language8,
			@Param("language9") String language9, @Param("language10") String language10,
			@Param("language11") String language11, @Param("language12") String language12,
			@Param("language13") String language13, @Param("language14") String language14,
			@Param("category") String category);
	
	@Query(value = "SELECT * FROM News n WHERE  n.language=:language1 or n.language=:language2 or n.language=:language3 or n.language=:language4 or n.language=:language5 or n.language=:language6 or n.language=:language7 or n.language=:language8 or n.language=:language9 or n.language=:language10 or n.language=:language11 or n.language=:language12 or n.language=:language13 or n.language=:language14   ORDER BY n.id  DESC LIMIT 1000", nativeQuery = true)
	List<News> getNewsAll(@Param("language1") String language1, @Param("language2") String language2,
			@Param("language3") String language3, @Param("language4") String language4,
			@Param("language5") String language5, @Param("language6") String language6,
			@Param("language7") String language7, @Param("language8") String language8,
			@Param("language9") String language9, @Param("language10") String language10,
			@Param("language11") String language11, @Param("language12") String language12,
			@Param("language13") String language13, @Param("language14") String language14);

	@Query(value = "SELECT * FROM News n WHERE  n.language=:language1 or n.language=:language2 or n.language=:language3 or n.language=:language4 or n.language=:language5 or n.language=:language6 or n.language=:language7 or n.language=:language8 or n.language=:language9 or n.language=:language10 or n.language=:language11 or n.language=:language12 or n.language=:language13 or n.language=:language14   ORDER BY n.id  DESC LIMIT 100", nativeQuery = true)
	List<News> getNotificationLang(@Param("language1") String language1, @Param("language2") String language2,
			@Param("language3") String language3, @Param("language4") String language4,
			@Param("language5") String language5, @Param("language6") String language6,
			@Param("language7") String language7, @Param("language8") String language8,
			@Param("language9") String language9, @Param("language10") String language10,
			@Param("language11") String language11, @Param("language12") String language12,
			@Param("language13") String language13, @Param("language14") String language14);
	
//	
//	@Query(value = "SELECT * FROM News n WHERE  n.language='English'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang1();
//	@Query(value = "SELECT * FROM News n WHERE n.language='English' or n.language='Hindi'  ",nativeQuery = true)
//	List<News> findNewsByLang2();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang3();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang4();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang5();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang6();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang7();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang8();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang9();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang10();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang11();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang12();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang13();
//	@Query(value = "SELECT * FROM News n WHERE  n.language='hindi'  ORDER BY n.id  DESC LIMIT 50" ,nativeQuery = true)
//	List<News> findNewsByLang14();

}