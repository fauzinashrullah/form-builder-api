package com.yourstechnology.formbuilder.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private List<AnswerQuestion> answers;
}
