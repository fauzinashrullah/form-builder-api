package com.yourstechnology.formbuilder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourstechnology.formbuilder.entity.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Optional<Response> findByFormIdAndUserId(Long formId, Long userId);
}
