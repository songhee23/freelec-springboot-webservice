package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
/*
@MappedSuperclass
- JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 합니다.
*/
@EntityListeners(AuditingEntityListener.class)
/*
@EntityListeners(AuditingEntityListener.class)
- BaseTimeEntity 클래스에 Auditing 기능을 포함시킵니다.
*/
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;
    /*
    @CreatedDate
    - Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
    */

    @LastModifiedDate
    private LocalDateTime modifiedDate;
    /*
    @LastModifiedDate
    - 조회한 Entity의 값을 변경할 때 시간이 자동 저장됩니다.
    */

    // -> Posts 클래스가 BaseTimeEntity를 상속받도록 변경합니다.
    // -> JPA Auditing 어노테이션들을 모두 활성화할 수 잇도록 Application 클래스에 활성화 어노테이션 추가
}