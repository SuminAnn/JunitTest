package com.example.junitproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.util.MailSender;
import com.example.junitproject.web.dto.BookRespDto;
import com.example.junitproject.web.dto.BookSaveReqDto;

// @DataJpaTest
@ExtendWith(MockitoExtension.class) //가짜 메모리 환경
public class BookServiceTest {

    // @Autowired
    // private BookRepository bookRepository; // 문제점 : 서비스만 테스트하고 싶은데 테스트가 끝난 레포지토리 레이어가 함께 테스트가 된다
    // 가짜 객체를 띄우기 위해서 @DataJpaTest를 주석처리하면 BookRepository가 메모리에 안뜬다

    @InjectMocks // 가짜환경(MOCK)을 의존성 주입을 해준다
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;
    
    @Test
    public void save_test(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Junit5");
        dto.setAuthor("Test");

        //stub : 가짜 객체의 행동정의를 해줘야한다
        when(bookRepository.save(any())).thenReturn(dto.toEntity()); //bookRepository.save() 매개변수에 dto.toEntity()가 들어가는 경우 hash code가 달라서 에러가 발생한다.
        when(mailSender.send()).thenReturn(true);

        //when
        // BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.save(dto);

        //then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitile());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
        // assertEquals(dto.getTitle(), bookRespDto.getTitile());
        // assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
    }
}

/*
  Mockito : 가짜 객체를 보관하는 환경
 */
