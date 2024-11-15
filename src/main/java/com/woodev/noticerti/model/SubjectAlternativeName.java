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
        name = "SUBJECT_ALTERNATIVE_NAME",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "PK_SUBJECT_ALTERNATIVE_NAME",
                        columnNames = {"id"}
                ),
                @UniqueConstraint(
                        name = "UK_SUBJECT_ALTERNATIVE_NAME",
                        columnNames = {"certificate_id", "domain"}
                )
        }
)
public class SubjectAlternativeName extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String domain;

    @ManyToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

    @Column(nullable = false)
    @Builder.Default
    private boolean isWildcard = false;
}
