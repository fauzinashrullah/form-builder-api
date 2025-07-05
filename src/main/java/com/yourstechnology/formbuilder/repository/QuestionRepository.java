package com.yourstechnology.formbuilder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourstechnology.formbuilder.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
    List<Question> findAllByFormId(Long formId);
    Optional<Question> findByIdAndFormId(Long id, Long formId);
}
