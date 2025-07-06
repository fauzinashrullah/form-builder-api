package com.yourstechnology.formbuilder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yourstechnology.formbuilder.entity.Form;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findBySlugAndCreatorId(String slug, Long creatorId);
    Optional<Form> findBySlug(String slug);
    List<Form> findAllByCreatorId(Long creatorId);
}
