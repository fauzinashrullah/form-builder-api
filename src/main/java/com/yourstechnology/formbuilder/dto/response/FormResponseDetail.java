package com.yourstechnology.formbuilder.dto.response;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class FormResponseDetail {
    private String date;
    private UserSummary user;
    private AnswerResponse answers;

}
