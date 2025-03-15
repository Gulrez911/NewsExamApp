package com.ctet.services.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ctet.common.PropertyConfig;
import com.ctet.web.dto.JobOne;

//@Service
public class SchedulerNews implements Runnable {

//	@Autowired
//	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(JobOne.class);

	private String jobName;
	String htmlLocation;

	PropertyConfig propertyConfig;
	
	public SchedulerNews(String jobName) {
		super();
		this.jobName = jobName;
	}

	@Override
	public void run() {

		logger.info("Running job {} at {}", jobName, LocalDateTime.now());
//		List<News> news = newsRepository.findAll();

//		EmailGenericMessageThread thread =  new EmailGenericMessageThread("gulfarooqui09@gmail.com", "Reset", "123", propertyConfig);
//		thread.run();
		
		
//		newsRepository
		System.out.println("Thread has ended");
	}

}
