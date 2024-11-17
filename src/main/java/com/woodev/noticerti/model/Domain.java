package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                        columnNames = {"host", "port"}
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_id", nullable = false)
    private SAN SAN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;
}
