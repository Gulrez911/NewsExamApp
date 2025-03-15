package com.ctet.data;

public enum UserType {

	STUDENT("STUDENT"), ADMIN("ADMIN"), EVALUATOR("EVALUATOR"), SUPER_ADMIN("SUPER_ADMIN"), REVIEWER("REVIEWER"), LMS_ADMIN("LMS_ADMIN"), LMS_STUDENT("LMS_STUDENT"),
	CORPORATE("CORPORATE");

	String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
