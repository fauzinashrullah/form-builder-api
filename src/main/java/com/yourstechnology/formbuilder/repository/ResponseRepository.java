package com.yourstechnology.formbuilder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yourstechnology.formbuilder.entity.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Optional<Response> findByFormIdAndUserId(Long formId, Long userId);
    List<Response> findAllByFormId(Long formId);
}
