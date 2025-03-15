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
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String sheadline;
	private String sdate;
	private String simage;
	private String surl;
	private String url;

	@Column(columnDefinition = "TEXT")
	private String mainheadline;
	private String maindate;
	private String mainImage;

//	@Column(length = 1000)
	@Column(columnDefinition = "TEXT")
	private String summary;

	Date createDate;
	Date updateDate;

	private String websiteName;
	private String category;
	private String language;

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

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMainheadline() {
		return mainheadline;
	}

	public void setMainheadline(String mainheadline) {
		this.mainheadline = mainheadline;
	}

	public String getMaindate() {
		return maindate;
	}

	public void setMaindate(String maindate) {
		this.maindate = maindate;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", sheadline=" + sheadline + ", sdate=" + sdate + ", simage=" + simage + ", surl="
				+ surl + ", url=" + url + ", mainheadline=" + mainheadline + ", maindate=" + maindate + ", mainImage="
				+ mainImage + ", summary=" + summary + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", websiteName=" + websiteName + ", category=" + category + ", language=" + language
				+ ", getWebsiteName()=" + getWebsiteName() + ", getCategory()=" + getCategory() + ", getLanguage()="
				+ getLanguage() + ", getSurl()=" + getSurl() + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getId()=" + getId() + ", getSheadline()=" + getSheadline()
				+ ", getSdate()=" + getSdate() + ", getSimage()=" + getSimage() + ", getUrl()=" + getUrl()
				+ ", getMainheadline()=" + getMainheadline() + ", getMaindate()=" + getMaindate() + ", getMainImage()="
				+ getMainImage() + ", getSummary()=" + getSummary() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
