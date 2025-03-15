package com.ctet.services.impl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.data.Category;
import com.ctet.repositories.CategoryRepository;
import com.ctet.services.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public void saveOrUpdate(Category category) {
		Category category2 = null;
		if (category.getId() != null) {
//			category2 = categoryRepository.findById(category.getId()).get();

			category2 = categoryRepository.getOne(category.getId());
		}

		if (category.getId() == null) {

			categoryRepository.save(category);

			System.out.println("save......category");
		} else {

//			for (Exam exam : category2.getExams()) {
//				System.out.println("////   " + exam.getId());
//			}
//			Category category3 = category2;
//			categoryRepository.delete(category2);

			category.setId(category2.getId());
//			List<Exam> exams = new ArrayList<>();
//
//			category.setExams(exams);
//			DozerBeanMapper dozerBean = new DozerBeanMapper();
//			  dozerBean.setMappingFiles(mappingFiles);
			
//			Mapper mapper = new DozerBeanMapper();
//
//			mapper.map(category, category2);
			categoryRepository.save(category);
		}

	}

}
