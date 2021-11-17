package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
/* @SpringBootApplication으로 인해 스프링 부트의 자동 설명, 스프링 Bean읽기와 생성을
모두 자동으로 설정합니다. 특히 @SpringBootApplication이 있는 위치부터 설정을 읽어나가가 때문에
항상 프로젝트 최상단에 위치해야만 합니다.
* */