package com.woodev.noticerti.dto;

import lombok.*;


/**
 * CertificateInfoDTO
 * <pre>
 *    인증서 정보를 담는 DTO
 *    - commonName: CN (Common Name) - 도메인명
 *    - issuingCA: 루트 CA
 *    - organization: 발급기관
 *    - validFrom: 유효기간 시작일
 *    - validTo: 유효기간 종료일
 *    - serialNumber: 일련번호
 *    - sans: SAN (Subject Alternative Names)
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainCertConnectDTO {
    private CertificateInfoDTO certificate;
    private SimpleDomainDTO domain;


}