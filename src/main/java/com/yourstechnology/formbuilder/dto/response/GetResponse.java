package com.yourstechnology.formbuilder.dto.response;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class GetResponse {
    private String date;
    private UserDTO user;
    private AnswerResponse answers;

}
