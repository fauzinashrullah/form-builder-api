package com.yourstechnology.formbuilder.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "allowed_domains")
public class AllowedDomains {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long formId;

    private List<String> domain;
}
