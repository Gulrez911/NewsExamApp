package com.ctet.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
//@Where(clause = "isDeleted='false'")
public class NewsVideo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String sheadline;
	private String sdate;
	private String simage;
	private String surl;
	private String url;

	private String maindate;
	private String mainVideoId;

	Date createDate;
	Date updateDate;

	private String websiteName;
	private String category;
	private String language;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSheadline() {
		return sheadline;
	}

	public void setSheadline(String sheadline) {
		this.sheadline = sheadline;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getSimage() {
		return simage;
	}

	public void setSimage(String simage) {
		this.simage = simage;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMaindate() {
		return maindate;
	}

	public void setMaindate(String maindate) {
		this.maindate = maindate;
	}

	public String getMainVideoId() {
		return mainVideoId;
	}

	public void setMainVideoId(String mainVideoId) {
		this.mainVideoId = mainVideoId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "NewsVideo [id=" + id + ", sheadline=" + sheadline + ", sdate=" + sdate + ", simage=" + simage
				+ ", surl=" + surl + ", url=" + url + ", maindate=" + maindate + ", mainVideoId=" + mainVideoId
				+ ", createDate=" + createDate + ", updateDate=" + updateDate + ", websiteName=" + websiteName
				+ ", category=" + category + ", language=" + language + "]";
	}

}
