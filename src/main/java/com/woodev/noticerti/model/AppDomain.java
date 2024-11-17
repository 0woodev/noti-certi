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
        name = "PROGRAM",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "PK_PROGRAM",
                        columnNames = {"id"}
                ),
                @UniqueConstraint(
                        name = "UK_APP_DOMAIN__APP_ID__DOMAIN_ID",
                        columnNames = {"app_id", "team_id"}
                ),
        }
)
public class AppDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;

}
