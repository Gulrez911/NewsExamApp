package com.ctet.common;

import java.util.ArrayList;
import java.util.List;

import com.ctet.web.dto.QuestionInstanceDto;

public class QuestionSequence {

	List<QuestionInstanceDto> questionInstanceDtos = new ArrayList<>();

	public QuestionSequence(List<QuestionInstanceDto> questionInstanceDtos) {
		this.questionInstanceDtos = questionInstanceDtos;
	}

	public QuestionInstanceDto nextQuestion(Long questionMapperId) {
		for (int i = 0; i < questionInstanceDtos.size(); i++) {
			QuestionInstanceDto dto = questionInstanceDtos.get(i);
			if (dto.getQuestionMapperInstance().getQuestionMapper().getId().equals(questionMapperId)) {
				if (i < (questionInstanceDtos.size() - 1)) {
					return questionInstanceDtos.get(i + 1);
				} else {
					return null;
				}
			}
		}

		return null;
	}

	public void scan(Long questionMapperId) {
		System.out.println(this.questionInstanceDtos.size());
		for (int i = 0; i < questionInstanceDtos.size(); i++) {
			QuestionInstanceDto dto = questionInstanceDtos.get(i);
			if (dto.getQuestionMapperInstance().getQuestionMapper().getId().equals(questionMapperId)) {
				System.out.println("location of q " + questionMapperId + " is at " + i + " index");
			}
		}

	}

	public QuestionInstanceDto previousQuestion(Long questionMapperId) {
		for (int i = questionInstanceDtos.size() - 1; i >= 0; i--) {
			QuestionInstanceDto dto = questionInstanceDtos.get(i);
			if (dto.getQuestionMapperInstance().getQuestionMapper().getId().equals(questionMapperId)) {
				if (i == 0) {
					return null;
				} else {
					return questionInstanceDtos.get(i - 1);
				}
			}
		}

		return null;
	}
}
