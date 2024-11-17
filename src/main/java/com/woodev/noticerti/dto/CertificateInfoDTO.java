package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.SAN;
import lombok.*;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.woodev.noticerti.util.X500Parser.CN;
import static com.woodev.noticerti.util.X500Parser.getValue;


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
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateInfoDTO {
    private String commonName;
    private String issuingCA;
    private String organization;
    private Instant validFrom;
    private Instant validTo;
    private String serialNumber;
    private List<String> sans;


    public CertificateInfoDTO(X509Certificate certificate) {
        this.commonName = getValue(certificate.getSubjectX500Principal().getName(), CN);
        this.issuingCA = getValue(certificate.getIssuerX500Principal().getName(), CN);
        this.organization = getValue(certificate.getIssuerX500Principal().getName(), "O");
        this.validFrom = certificate.getNotBefore().toInstant();
        this.validTo = certificate.getNotAfter().toInstant();
        this.serialNumber = certificate.getSerialNumber().toString();

        // SAN (Subject Alternative Names)
        try {
            this.sans = certificate.getSubjectAlternativeNames().stream()
                    .map(o -> o.get(1).toString())
                    .toList();
        } catch (CertificateParsingException e) {
            this.sans = null;
        }
    }

    public CertificateInfoDTO(Certificate certificate, List<SAN> sans) {
        this.commonName = certificate.getCommonName();
        this.issuingCA = certificate.getIssuingCA();
        this.organization = certificate.getOrganization();
        this.validFrom = certificate.getValidFrom();
        this.validTo = certificate.getValidTo();
        this.serialNumber = certificate.getSerialNumber();
        this.sans = sans == null ? new ArrayList<>() : sans.stream().map(SAN::getHost).toList();
    }

    public String getSimpleSAN() {
        if (sans == null) return "";

        else if (sans.size() <= 1) return sans.get(0);

        return sans.get(0) + " 외 " + (sans.size() - 1) + "개";
    }

    public String getPrimaryKey() {
        return this.issuingCA + this.serialNumber;
    }

    @Override
    public String toString() {
        return "CertificateInfoDTO{" + "\n" +
                "  commonName='" + commonName + "'\n" +
                "  issuingCA='" + issuingCA + "'\n" +
                "  organization='" + organization + "'\n" +
                "  validFrom=" + validFrom + "'\n" +
                "  validTo=" + validTo + "'\n" +
                "  serialNumber='" + serialNumber + "'\n" +
                "  subjectAlternativeNames=" + getSimpleSAN() + "'\n" +
                "}\n";
    }

    public Certificate toEntity() {
        return Certificate.builder()
                .commonName(commonName)
                .issuingCA(issuingCA)
                .organization(organization)
                .validFrom(validFrom)
                .validTo(validTo)
                .serialNumber(serialNumber)
                .build();
    }
}