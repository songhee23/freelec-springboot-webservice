package com.jojoldu.book.springboot.web;

import org.apache.catalina.security.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
/* @RunWith(SpringRunner.class)
 *  - 테스트를 진행할 때 Junit에 내장된 실행자 되에 다른 실행자를 실행시킵니다.
 *  - 여기서는 SpringRunner라는 스프링 실행자를 사용합니다.
 *  - 즉, 스프링 부트 테스트와 Jnunit사이에 연결자 역할을 합니다.
 * */
@WebMvcTest(controllers = HelloController.class)
/* @WebMvcTest
 - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션 입니다.
 - 선언할 경우, @Controller, @ControllerAdvice등을 사용할 수 있습니다.
 - 단, @Service, @Component, @Repository 등은 사용할 수 없습니다.
 - 여기서는 컨트롤러만 사용하기 때문에 선언합니다.
* */
public class HelloControllerTest {

    @Autowired
    /* @Autowired
    - 스프링이 관리하는 빈(bean)을 주입 받는다.
    * */
    private MockMvc mvc;
    /* private MockMvc mvc
    - 웹 API를 테스트할 때 사용합니다.
    - 스프링 MVC 테스트의 시작점입니다.
    - 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 진행할 수 있습니다.
    *
    * */

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
        /* mvc.perform(get("/hello"))
        - MockMvc를 통해 /hello 주소로 HTTP GET요청을 합니다.
        - 체이닝이 지원되어 아래와 같은 검증 기능을 이어서 선언할 수 있다.
        * */

        /* .andExpect(status().isOk())
        - mvc.perform의 결과를 검증합니다.
        - HTTP Header의 Status를 검증합니다.
        - 우리가 흔히 알고 있는 200, 404, 500 등의 상태를 검증합니다.
        - 즉, 여기서 OK, 200인자 아닌지를 검증합니다.
        * */

        /* .andExpect(content().string(hello))
        - mvc.perform의 결과를 검증합니다
        - 응답 본문의 내용을 검증합니다.
        - Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.
        *
        * */

    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

        /*
        1. Param
        - API 테스트 할 때 사용될 요청 파라미터를 설정합니다
        - 단, 값은 String만 허용 됩니다.
        - 그래서 숫자/날짜 들의 데이터들도 등록할 때는 문자열로 변경해야만 가능

        2. jsonPath
        - JSON 응답값을 필드별로 검증할 수 있는 메소드입니다.
        - $를 기준으로 필드명을 명시합니다.
        - 여기서는 name과 amount를 검증하니, $.name, $.amount로 검증합니다.
         */

        /*
        chapter3 스프링부트에서 JPA로 데이터베이스 다뤄보자
        - 어떻게 하면 관계현 데이터베이스를 이용하는 프로젝트에서 객제지향 프로그래밍을 할 수 있을까 고민했습니다.
          문제의 해결책으로 JPA라느 자바 표준 ORM(Object Relational Mapping) 기술을 만나게 됩니다.
        - Mybatis, iBatissms ORM이 아닙니다. SQL Mapper입니다. 가끔 ORM에 대해 Mybatis, iBatis를 얘기하는데, 이둘은 ORM이 아닙니다.
        - ORM은 객체를 매핑하는 것이고, SQL Mapper는 쿼리를 매핑합니다.

        왜 JPA를 사용하여야 하는가?
        관계형 데이터베이스로 구축하다보니 프로젝트 대부분이 애플리케이션코드보다 SQL로 가득 차게 된 것이다.
        관계형 데이터베이스가 SQL만 인식할 수 있기 때문에 각 테이블마다 기본적인 CRUD(create, read, update, delete) SQL을 매번 생성해야 합니다.
        개발자가 아무리 자바 클래스를 아름답게 설계해도 SQL을 통해서만 데이터베이스에 저장하고 조회할 수 있다.

        --> 단순 반복 작업 문제

        관계형 데이터베이스로 객체지향을 표현할 수 없음
        객체지향에서의 부모 객체를 가져올 때

        User user = findUser();
        Group group = user.getGroup();

        -> 누구나 명확하게 User와 Group은 부모-자식 관계임을 알 수 있다.

        하지만 데이터베이스가 추가되면?

        User user = userDao.findUser();
        Group group = groupDao.findGroup(user.getGroupId());
        -> User따로, Group따로 조회하게 된다. User와 Group이 어떤 관계인지 알 수 없음
        상속, 1:N 등 다양한 객체 모델링을 데이터베이스로 구현할 수 없다.

        이를 해결하기 위해 JPA등장하게 됨

        JPA <- Hibernate <- Spring Data JPA
        구현체들을 좀 더 쉽게 사용하고자 추상화시킨 Spring Data Jpa모듈 이용하여 JPA기술을 다룬다.

        이렇게 한 단계 더 감싸놓은 Spring Data JPA가 등장한 이유는 크게 두가지다.
        - 구현제 교체의 용의성
        - 저장소 교체의 용의성

        구현체 교체의 용의성이란? Hibernate외에 다른 구현체로 쉽게 교체하기 위함
        ex) redis클라이언트가 jedis에서 lettuce로 대세가 넘어갈 때 Spring Data Redis쓴 분들은 쉽게 교체함

        저장소 교체의 용의성이란? 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함
        서비스 초기에는 관계형 데이터베이스로 모든 기능을 처리했지만, 점점 트래픽이 많아져 관계형 데이터베이스로는
        도저히 감당이 안될 때가 있습니다. 이때 MongoDB로 교체가 필요하다면 개발자는 Spring Data JPA에서
        Spring Data MongoDB로 의존성만 교체하면 됩니다.

        이는 Spring Data의 하위 프로젝트들은 기본적인 CRUD 의 인터페이스가 갇기 때문입니다.

        JPA를 다루면? 가장 먼저 CRUD쿼리를 직접 작성할 필요가 없습니다. 부모-자식 관계표현, 1:N관계표현, 상태와
        행위를 한 곳에서 관리하는 등 객체지향 프로그래밍을 쉽게 할 수 있습니다.

        속도이슈? 대용량


        * */

    }

}