package com.ctet.web.dto;

public class NewsDto {

	private String url;
	private String mainheadline;
	private String maindate;
	private String mainImage;
	private String summary;

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
		return "NewsDto [url=" + url + ", mainheadline=" + mainheadline + ", maindate=" + maindate + ", mainImage="
				+ mainImage + ", summary=" + summary + "]";
	}

}
