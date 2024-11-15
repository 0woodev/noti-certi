package com.woodev.noticerti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
                        columnNames = {"ID"}
                ),
                @UniqueConstraint(
                        name = "UK_PROGRAM__CODE",
                        columnNames = {"CODE"}
                )
        }
)
public class Program extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String programName;

    private String code;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
