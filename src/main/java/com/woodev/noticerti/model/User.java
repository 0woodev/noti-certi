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
        name = "USR", // H2 DB에서는 USER 이름을 사용할 수 없다.
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "PK_USR",
                        columnNames = {"id"}
                ),
                @UniqueConstraint(
                        name = "UK_USR__CODE",
                        columnNames = {"code"}
                )
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Column(nullable = false, unique = true)
    private String code;
}
