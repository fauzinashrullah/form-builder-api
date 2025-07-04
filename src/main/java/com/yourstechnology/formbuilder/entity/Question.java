package com.yourstechnology.formbuilder.entity;

import java.util.List;

import com.yourstechnology.formbuilder.util.ChoiceType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "questions")
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long formId;

    private String name;

    @Enumerated(EnumType.STRING)
    private ChoiceType choiceType;

    private List<String> choices;

    private Boolean isRequired;
}
