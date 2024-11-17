package com.woodev.noticerti.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * SAN : Subject Alternative Name
 * <p>
 *     SAN (Subject Alternative Name)은 인증서에 포함된 도메인명을 의미합니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "SAN",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "PK_SAN",
                        columnNames = {"id"}
                ),
                @UniqueConstraint(
                        name = "UK_SAN__CERTIFICATE_ID__HOST",
                        columnNames = {"certificate_id", "host"}
                )
        }
)
public class SAN extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String host;

    @ManyToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

    @Column(nullable = false)
    @Builder.Default
    private boolean isWildcard = false;
}
