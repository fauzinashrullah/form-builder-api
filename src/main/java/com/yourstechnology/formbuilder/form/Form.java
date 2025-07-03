package com.yourstechnology.formbuilder.form;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "forms")
@Data
public class Form {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private List<String> allowedDomains;
    private String description;
    private Boolean limitOneResponse;


    private Long creatorId;
}
