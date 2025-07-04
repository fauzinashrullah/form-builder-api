package com.yourstechnology.formbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourstechnology.formbuilder.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByResponseId(Long responseId);
}
