package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.Certificate;
import lombok.*;

import java.time.Instant;


/**
 * SimpleCertDTO
 * <pre>
 *    인증서 정보를 담는 DTO
 *    - id
 *    - validTo: 유효기간 종료일
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCertDTO {
    @Setter
    private Long id;
    private Instant validTo;

    public SimpleCertDTO(Certificate certificate) {
        this.id = certificate.getId();
        this.validTo = certificate.getValidTo();
    }
}