package com.yourstechnology.formbuilder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String name;
    private String email;
    private String emailVerifiedAt;
}
