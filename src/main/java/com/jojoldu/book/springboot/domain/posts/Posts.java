package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
domain패키지는 도메인을 담을 패키지 입니다. 여기서 도메인이란 게시물, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한
요구사항 혹은 문제 영역이라고 생각하시면 됩니다.
기존에 Mybatis와 같은 쿼리 매퍼를 사용했다면, dao 패키지를 떠올리겠지만, dao 패키지와 조금 결이 다르다고 생각하면 됩니다.
그간 xml에 쿼리를 담고, 클래스는 오로지 쿼리의 결과만 담던 일들이 모두 도메인 클래스라고 불리는 곳에서 해결됩니다.
도메인을 좀 더 자세하게 공부해보고 싶으신 분들은 DDD Start-최범균 책을 참고해보길 추천합니다.
**/
@Getter // lombok의 어노테이션
@NoArgsConstructor // lombok의 어노테이션
@Entity // JPA의 어노테이션 *
/*
롬복은 코드를 단순화시켜주지만 필수 어노테이션은 아닙니다. 그러다 보니 주요 어노테이션인 @Entity를 클래스
가깝게 두고, 롬복 어노테이션을 그 위로 두었습니다. 이렇게 하면 이후에 코들린 들의 새 언어 전환으로 롬복이 더이상
필요 없을 경우 쉽게 삭제할 수 있음
* */
/*
@Entity
- 테이블과 링크될 클래스임을 나타냅니다.
- 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭합니다.
ex) SalesManager.java -> sales_manager table
* */
public class Posts {

    @Id // 해당 테이블의 PK 필드를 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    @GeneratedValue
    - PK의 생성 규칙을 나타냅니다.
    - 스프링부트 2.0에서는 GenerationType.IDENTITY 옵션이 추가되어야만 auto_increment가 됩니다.
    * */

    @Column(length = 500, nullable = false)
    private String title;
    /*
    @Column
    - 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됩니다.
    - 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용합니다.
    - 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나, ex) title
      타입을 TEXT오 변경하고 싶거나 ex) content등의 경우에 사용됩니다.
    * */

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}