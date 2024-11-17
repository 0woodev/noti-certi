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
                        name = "PK_APP",
                        columnNames = {"id"}
                ),
                @UniqueConstraint(
                        name = "UK_APP__TEAM_ID__CODE",
                        columnNames = {"team_id", "code"}
                ),
        }
)
public class App extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String appName;

    private String code;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
