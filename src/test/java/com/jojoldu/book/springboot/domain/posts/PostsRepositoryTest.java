package com.jojoldu.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }
    /*
    @After
    - junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    - 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용합니다.
    - 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 잇어 다음 테스트 실행시 테스트가 실패할 수 있습니다.

    * */

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());
        /*
        postRepository.save
        - 테이블 posts에 insert/update 쿼리를 실행합니다.
        - id값이 있다면 update가, 없다면 insert 쿼리가 실행됩니다.
        * */

        //when
        List<Posts> postsList = postsRepository.findAll();
        /*
        postsRepository.findAll
        - 테이블 posts에 있는 모든 데이터를 조회해오는 메소드입니다.
        * */

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
    /*
    실제로 실행된 쿼리는 어떤 형태일까?
    실행된 쿼리를 로그로 볼 수 없을까요? 물론 퀄리 로그를 ON/OFF 할 수 있는 설정이 있습니다. 다만 이런 설정들을 Java클래스로 구현할 수 있으나
    스프링 부트에서는 application.properties, application.yml 등의 파일로 한 줄의 코드로 설정할 수 있도록 지원하고 권장하니 이를 사용하겠음

    src/main/resource 디렉토리 아래에 application.properties파일 생성

    * */

}