package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "PK_CERTIFICATE",
                columnNames = {"id"}
        ),
        @UniqueConstraint(
                name = "UK_CERTIFICATE",
                columnNames = {"issuingCA", "serialNumber"}
        )
})
public class Certificate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String commonName;      // CN
    private String issuingCA;       // 루트 CA
    private String organization;    // O
    private String serialNumber;    // 일련번호

    private Instant validFrom;       // 유효기간 시작일
    private Instant validTo;         // 유효기간 종료일
}
