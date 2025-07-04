package com.yourstechnology.formbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourstechnology.formbuilder.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
    
}
