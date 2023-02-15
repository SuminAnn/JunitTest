package com.example.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


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

    // 책 삭제
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
*/
