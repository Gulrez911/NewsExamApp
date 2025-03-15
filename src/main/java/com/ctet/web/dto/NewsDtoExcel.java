package com.ctet.web.dto;

public class NewsDtoExcel {

	private String websiteName;
	private String mainLink;
	private String category;
	private String language;
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getMainLink() {
		return mainLink;
	}

	public void setMainLink(String mainLink) {
		this.mainLink = mainLink;
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
		return "NewsDtoExcel [websiteName=" + websiteName + ", mainLink=" + mainLink + ", category=" + category
				+ ", language=" + language + ", page=" + page + "]";
	}

}
