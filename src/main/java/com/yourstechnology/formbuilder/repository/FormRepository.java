package com.yourstechnology.formbuilder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourstechnology.formbuilder.entity.Form;


public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findBySlugAndCreatorId(String slug, Long creatorId);
    Optional<Form> findBySlug(String slug);
}
