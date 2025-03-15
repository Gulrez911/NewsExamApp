package com.ctet.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctet.data.News;
import com.ctet.data.User;
import com.ctet.repositories.NewsRepository;
import com.ctet.web.dto.NewsDto;

@Controller
public class DemoController {

	@Autowired
	NewsRepository newsRepository ;
	
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> demoApi() {

		Map<String, Object> map = new HashMap<String, Object>();
		NewsDto dd = new NewsDto();
		dd.setMaindate(new Date().toString());
		dd.setMainheadline("Aaron Carter accidentally drowned after taking drugs");
		dd.setMainImage(
				"https://ichef.bbci.co.uk/news/976/cpsprodpb/1ACC/production/_129406860_mediaitem129406859.jpg.webp");
		map.put("news", dd);
		return map;
	}
	
	
	
	
	
	@org.springframework.web.bind.annotation.GetMapping({ "/getUserDetails/{mobile}" })
	public ResponseEntity<?> getUserDetails(
			@org.springframework.web.bind.annotation.PathVariable("mobile") Long mobile) {
		System.out.println("method called");

		List<News> news = null;
		try {
			news = newsRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (news == null) {
			return ResponseEntity.ok().body("No_User_Exists");
		}
 
		System.out.println("repo:  " + news);
		return ResponseEntity.ok().body(news);
	}
	
	
	
	
	
}
