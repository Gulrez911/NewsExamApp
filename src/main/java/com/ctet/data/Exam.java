package com.ctet.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.data.repository.cdi.Eager;

@Entity
@Eager
public class Exam extends Base {

	String examName;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "examid")

//	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToMany(cascade = CascadeType.ALL)

	@OneToMany(fetch = FetchType.EAGER)
	List<Test> tests2 = new ArrayList<>();

	@Transient
	List<String> tests = new ArrayList<>();

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<Test> getTests2() {
		return tests2;
	}

	public void setTests2(List<Test> tests2) {
//		this.tests2 = tests2;

		this.tests2 = tests2;
		List<String> ss = new ArrayList<>();
		for (Test test : tests2) {
			ss.add(test.getTestName());
		}
		setTests(ss);

	}

	public List<String> getTests() {
		return tests;
	}

	public void setTests(List<String> tests) {
		this.tests = tests;
	}

	@Override
	public String toString() {
		return "Exam [examName=" + examName + ", tests2=" + tests2 + ", tests=" + tests + "]";
	}

}