package com.ctet.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class TestLinkTime extends Base {

	String testName;

	Long testId;

	Date startDate;

	Date endDate;

	@Transient
	String stDate;

	@Transient
	String edDate;

	@Column(length = 1000)
	String url;

	Boolean dontCheckTimeValidity = false;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getStDate() {
		if (this.getStartDate() == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		return dateFormat.format(this.startDate);
	}

	public void setStDate(String stDate) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		this.stDate = stDate;
		Date date = dateFormat.parse(stDate);
		setStartDate(date);
	}

	public String getEdDate() {
		if (this.getEndDate() == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		return dateFormat.format(this.endDate);
	}

	public void setEdDate(String edDate) throws ParseException {
		this.edDate = edDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		Date date = dateFormat.parse(edDate);
		setEndDate(date);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void generateLink() {

	}

	public Boolean getDontCheckTimeValidity() {
		return dontCheckTimeValidity == null ? false : dontCheckTimeValidity;
	}

	public void setDontCheckTimeValidity(Boolean dontCheckTimeValidity) {
		this.dontCheckTimeValidity = dontCheckTimeValidity;
	}

}