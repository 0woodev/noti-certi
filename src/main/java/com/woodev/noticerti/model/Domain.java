package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Domain : Host, Port 정보만 가지고 있는 테이블
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "SERVICE_DOMAIN",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "PK_DOMAIN",
                        columnNames = {"id"}
                ),
                @UniqueConstraint(
                        name = "UK_DOMAIN__DOMAIN_PORT",
                        columnNames = {"ip", "port"}
                )
        })
public class Domain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private int port;

    @Column(nullable = false)
    private String ip;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

}
