package com.yourstechnology.formbuilder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long formId;
    private Long userId;
    private String date;
}
