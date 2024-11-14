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
public class Certificate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    private String commonName;      // CN
    private String issuingCA;       // 루트 CA
    private String organization;    // O
    private String serialNumber;    // 일련번호

    private String validFrom;       // 유효기간 시작일
    private String validTo;         // 유효기간 종료일

    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SubjectAlternativeName> sans = new ArrayList<>(); // SAN (Subject Alternative Names)
}
