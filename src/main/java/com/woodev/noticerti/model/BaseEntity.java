package com.woodev.noticerti.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * <pre>
 * - @MappedSuperclass : 이 클래스를 상속받는 Entity 클래스에 BaseEntity의 필드를 추가한다.
 * - @EntityListeners(AuditingEntityListener.class) : JPA Entity에 Auditing 기능을 추가한다.
 * </pre>
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
