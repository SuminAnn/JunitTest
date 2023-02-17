package com.example.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest //DB와 관련된 컴포넌트들만 메모리에 로드
public class BookRepositoryTest {
    
    @Autowired //DI
    private BookRepository bookRepository;

    @BeforeEach //각 테스트 시작전에 한번만 실행, @BeforeAll : 테스트 진행전에 한번만 실행
    public void insert_data(){
        String title = "Junit5";
        String author = "Test";

        Book book = Book.builder()
                    .title(title)
                    .author(author)
                    .build();
        bookRepository.save(book);
    } // 다음 메서드까지만 트랜잭션 유지
    // 책 등록
    @Test
    public void save_test(){
        //given(데이터 준비)
        String title = "Junit5";
        String author = "Test";

        Book book = Book.builder()
                    .title(title)
                    .author(author)
                    .build();

        //when(테스트 실행)
        Book bookPS = bookRepository.save(book);

        //then(검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // 트랜잭션 종료(저장된 데이터 초기화) -> 각각 테스트는 분리를 시키는게 좋다

    // 책 목록보기
    @Test
    public void selectAll_test(){
        //given
        String title = "Junit5";
        String author = "Test";

        //when
        List<Book> booksPS = bookRepository.findAll();

        //then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }// 트랜잭션 종료(저장된 데이터 초기화) -> 각각 테스트는 분리를 시키는게 좋다

    // 책 한건보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void select_test(){
        //given
        String title = "Junit5";
        String author = "Test";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }// 트랜잭션 종료(저장된 데이터 초기화) -> 각각 테스트는 분리를 시키는게 좋다

    // 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void update_test(){
        //given
        Long id = 1L;
        String title = "junit5";
        String author  = "update";
        Book book = Book.builder()
                    .id(id)
                    .title(title)
                    .author(author)
                    .build();

        //when
        Book bookPS = bookRepository.save(book);

        bookRepository.findAll().stream()
            .forEach((b) -> {
                System.out.println(b.getAuthor());
            });


        //then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void delect_test(){
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        //then
        assertFalse(bookRepository.findById(id).isPresent());

    }
}

/*
*테스트 순서
 클라이언트 -> 요청 ->  Contorller              -   Service                                -   Repository
                    (3)클라이언트와 테스트          (2)기능들이 트랜잭션을 잘 타는지             (1) DB쪽 관련 테스트

* 테스트
 -> 스프링 부트 실행
 -> 테스트 실행(테스트 환경)
 통합 테스트는 전부를 다 테스트
 단위테스트 (특정 부분만 메모리에 로드)

* 테스트는 순서가 보장이 안된다 -> 순서를 보장하고 싶은경우 @Order(번호)를 활용
* 테스트 메서드가 하나 실행 후 종료되면 데이터가 초기화된다.(@Transactional로 인해)
 - PK(auto_increment) 값이 초기화가 안된다
   -> 해결방법 : @Sql 활용 (id를 활용하는 test에서는 테이블을 drop후에 다시 생성해주는것이 좋다)
*/
