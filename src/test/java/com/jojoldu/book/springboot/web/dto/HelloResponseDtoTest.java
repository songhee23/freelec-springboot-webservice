package com.jojoldu.book.springboot.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;  // main one


public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
        // assertThat(actual).isEqualTo(Expected);
        // 왼쪽에서 오른쪽으로 자연스럽게 읽힌다.
        /* 1. assertJ
        - assertJ라는 테스트 검증 라이브러리의 검증 메소드입니다.
        - 검증하고 싶은 대상을 메소드 인자로 받습니다.
        - 메소드 체이닝이 지원되어 isEqualTo와 같이 메소드를 이어서 사용할 수 있습니다.
        * */
    }

    // assertJ 테스트 참조
    // https://pjh3749.tistory.com/241

    @Test
    public void a_few_simple_assertions() {
        assertThat("The Lord of the Rings")
                .isNotNull()
                .startsWith("The")
                .contains("Lord")
                .endsWith("Rings");
    }

}