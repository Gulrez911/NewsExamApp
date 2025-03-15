package com.ctet.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Category extends Base {

	String categoryName;

//	@ManyToMany(fetch = FetchType.EAGER)

//	@OneToMany(cascade = CascadeType.MERGE)
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

//	@ManyToMany(fetch = FetchType.EAGER)

//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(fetch = FetchType.EAGER)
	List<Exam> exams = new ArrayList<>();

	@Transient
	List<String> exList = new ArrayList<>();

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
//		this.exams = exams;

		this.exams = exams;
		List<String> ss = new ArrayList<>();
		for (Exam exam : exams) {
			ss.add(exam.examName);
		}
		setExList(ss);

	}

	public List<String> getExList() {
		return exList;
	}

	public void setExList(List<String> exList) {
		this.exList = exList;
	}

}