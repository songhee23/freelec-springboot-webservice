package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    /*
    여기서 Entity 클래스와 거의 유사한 형태입에도 Dto클래스를 추가로 생성했습니다.
    하지만 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안됩니다 .. (왜일까..?)

    Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스 입니다. Entity클래스를 기준으로 테이블이 생성되고, 스키마다 변경됩니다. 화면 변경은 아주
    사소한 기능 변경인데, 이를 위해 테이블과 연결괸 Entity 클래스를 변경하는 것은 너무 큰 변경입니다.

    수많은 서비스 클래스나 비즈니스 로직들이 Entity 클래스를 기준으로 동작합니다. Entity클래스가 변경되면 여러 클래스에 영향을 주지만,
    Request와 Response용 Dto는 View를 위한 클래스라 정말 자주 변경이 필요합니다.

    View Layer와 DB layer의 역할 분리를 철저하게 하는 게 좋습니다.
    실제로 Controller에서 결과값으로 여러 테이블을 조인 해서 줘야 할 경우가 빈번하므로
    Entity클래스만으로 표현하기가 어려운 경우가 많습니다.

    ** 꼭 Entity 클래스와 Controller에서 쓸 Dto는 분리해서 사용해야 합니다. **
    * */
}