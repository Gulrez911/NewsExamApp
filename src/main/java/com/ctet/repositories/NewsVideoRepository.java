package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.News;
import com.ctet.data.NewsVideo;

@Repository
public interface NewsVideoRepository extends JpaRepository<NewsVideo, Long> {

	@Query("SELECT n FROM NewsVideo n WHERE  n.simage=:simage or n.sheadline=:sheadline")
	List<NewsVideo> findTestBySimage2(@Param("simage") String simage,@Param("sheadline") String sheadline);
	
	
	@Query(value = "SELECT * FROM NewsVideo n ORDER BY n.id  DESC LIMIT 20" ,nativeQuery = true )
	public List<NewsVideo> findAllByOrderByIdDesc();
}