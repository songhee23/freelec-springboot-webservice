package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
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
@Getter // lombok의 어노테이션, 클래스 내 모든 Getter 메소드를 자동 생성
@NoArgsConstructor // lombok의 어노테이션, 기본 생성자 자동 추가, public Posts() {}와 같은 효과, 파라미터가 없는 기본 생성자을 생성해줌
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
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK 필드를 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    @GeneratedValue
    - PK의 생성 규칙을 나타냅니다.
    - 스프링부트 2.0에서는 GenerationType.IDENTITY 옵션이 추가되어야만 auto_increment가 됩니다.

    웬만하면 Entity의 PK는 Long타입의 Auto_increment를 추천합니다.(Mysql기준으로 이렇게 하면 bigint 타입이 추가됩니다.)
    여러 키를 조합한 복합키로 PK를 잡을 경우 난감한 상황이 종종 발생
    1) fk를 맺을 때 다른 테이블에서 복합키를 전부 갖고 있거나, 중간 테이블을 하나 더 둬야 하는 상황 발생
    2) 인덱스에 좋은 영향을 끼치지 못함
    3) 유니크한 조건이 변경될 경우, PK 전체를 수정해야 하는 일 발생
    * */

    @Column(length = 500, nullable = false)
    private String title;
    /*
    @Column
    - 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됩니다.
    - 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용합니다.
    - 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나,(ex, title)
      타입을 TEXT로 변경하고 싶거나(ex, content)등의 경우에 사용됩니다.
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
    /*
    @Builder
    - 해당 클래스의 빌더 패턴 클래스를 생성
    - 생성자 상단에 선언시, 생성자에 포함된 필드만 빌더에 포함
    * */

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
/*
이 Posts 클래스에는 한가지 특징이 있다. 바로 Setter 메소드가 없다는 점
자바 빈 규약을 생각하면서 getter/setter를 무작정 생성하는 경우가 있습니다.
이렇게 되면 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확하게 구분할 수가 없어,
차후 기능 변경시 정말 복잡해 집니다.
그래서 Entity클래서에서는 절대 Setter메소드를 만들지 않습니다.
대신, 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야 합니다.

Setter가 없는 이 상황에서 어떻게 값을 채워 DB에 삽입 해야 할까요?
기본적인 구조는 생성자를 통해 최종 값을 채운 후 DB에 삽입하는 것이며, 값 변경이 필요한 경우, 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 합니다.
이 책에서는 생성자 대신에 @Builder를 통해 제공되는 빌더 클래스를 사용 합니다. 생성자나 빌더나 생성 시점에 값을 채워주는 것은 똑같다.
하지만 빌더를 사용하게 되면 어느 필드에 어떤 값을 패워야 할지 명확하게 인지할 수 있다.
* */