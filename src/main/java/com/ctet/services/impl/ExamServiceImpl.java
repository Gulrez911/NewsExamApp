package com.ctet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.data.Exam;
import com.ctet.repositories.ExamRepository;
import com.ctet.services.ExamService;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	@Autowired
	ExamRepository examRepository;

	@Override
	public void saveOrUpdate(Exam exam) {
		Exam exam2 = null;
		if (exam.getId() != null) {
//			category2 = categoryRepository.findById(category.getId()).get();
			exam2 = examRepository.getOne(exam.getId());
		}
		if (exam.getId() == null) {
			examRepository.save(exam);
			System.out.println("save......category");
		} else {
			exam.setId(exam2.getId());
			examRepository.save(exam);
		}

	}

}
