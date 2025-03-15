package com.ctet.data;

public enum QuestionType {

	MCQ("MCQ"), CODING("CODING"), FULL_STACK_JAVA("FULL_STACK_JAVA"), FULLSTACK("FULLSTACK"), FILL_BLANKS_MCQ("FILL_BLANKS_MCQ"),
	MATCH_FOLLOWING_MCQ("MATCH_FOLLOWING_MCQ"), IMAGE_UPLOAD_BY_USER("IMAGE_UPLOAD_BY_USER"), VIDEO_UPLOAD_BY_USER("VIDEO_UPLOAD_BY_USER"),
	SUBJECTIVE_TEXT("SUBJECTIVE_TEXT");

	String type;

	private QuestionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}