package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * 아직 적용 전
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CertificateHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long ServiceDomainId;   // 서비스 도메인 ID
    private String serviceDomainName; // 서비스 도메인명

    private Long certificateId;     // 인증서 ID
    private String commonName;      // CN
    private String issuingCA;       // 루트 CA
    private String organization;    // O
    private String serialNumber;    // 일련번호

    private String validFrom;       // 유효기간 시작일
    private String validTo;         // 유효기간 종료일

    private String programId;       // 프로그램 ID
    private String programName;     // 프로그램명
    private String programCode;     // 코드
}
