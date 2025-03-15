package com.ctet.web.dto;

public class NewsVideoDto {

	private String url;
	private String mainheadline;
	private String maindate;
	private String videoId;

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

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	@Override
	public String toString() {
		return "NewsVideoDto [url=" + url + ", mainheadline=" + mainheadline + ", maindate=" + maindate + ", videoId="
				+ videoId + "]";
	}

}
