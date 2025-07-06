package com.yourstechnology.formbuilder.entity;

import java.util.List;

import com.yourstechnology.formbuilder.model.ChoiceType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Byte isRequired;
}
