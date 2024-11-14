package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Domain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String domain;


    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SubjectAlternativeName> subjectAlternativeName = new ArrayList<>();
}
