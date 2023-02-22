package com.example.junitproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.util.MailSenderStub;
import com.example.junitproject.web.dto.BookRespDto;
import com.example.junitproject.web.dto.BookSaveReqDto;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository; // 문제점 : 서비스만 테스트하고 싶은데 테스트가 끝난 레포지토리 레이어가 함께 테스트가 된다
    
    @Test
    public void save_test(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Junit5");
        dto.setAuthor("Test");
        //stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        //when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.save(dto);

        //then
        assertEquals(dto.getTitle(), bookRespDto.getTitile());
        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
    }
}

/*
  Mockito : 가짜 객체를 보관하는 환경
 */
