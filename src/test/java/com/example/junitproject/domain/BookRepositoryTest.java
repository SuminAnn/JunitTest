package com.example.junitproject.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest //DB와 관련된 컴포넌트들만 메모리에 로드
public class BookRepositoryTest {
    
    @Autowired //DI
    private BookRepository bookRepository;

    // 책 등록
    @Test
    public void save_test(){
        System.out.println("책등록_test 실행");
    }

    // 책 목록보기

    // 책 한건보기

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
