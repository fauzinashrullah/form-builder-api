package com.yourstechnology.formbuilder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yourstechnology.formbuilder.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
    List<Question> findAllByFormId(Long formId);
    Optional<Question> findByIdAndFormId(Long id, Long formId);
}
