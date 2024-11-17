package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * App : 앱 <br>
 * 앱은 팀에 속해있으며, 여러 개의 앱 도메인을 가질 수 있다.
 * <p>
 * ex)
 * - 앱 : 러닝라운지, 해외딜러, HMGPrime, ...
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "APP",
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
