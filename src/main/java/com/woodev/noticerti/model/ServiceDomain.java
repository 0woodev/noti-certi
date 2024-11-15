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
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "PK_SERVICE_DOMAIN",
                columnNames = {"id"}
        ),
        @UniqueConstraint(
                name = "UK_SERVICE_DOMAIN__DOMAIN_PORT",
                columnNames = {"domain", "port"}
        )
})
public class ServiceDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private int port;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_id", nullable = false)
    private SubjectAlternativeName subjectAlternativeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
}
