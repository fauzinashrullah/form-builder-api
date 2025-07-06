package com.yourstechnology.formbuilder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yourstechnology.formbuilder.entity.AllowedDomain;

@Repository
public interface DomainRepository extends JpaRepository<AllowedDomain,Long>{
    Optional<AllowedDomain> findByFormId(Long formId);
}
