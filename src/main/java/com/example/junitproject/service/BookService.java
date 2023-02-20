package com.example.junitproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.web.dto.BookRespDto;
import com.example.junitproject.web.dto.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository; //final은 객체 생성 시점에 값이 들어와야한다(@RequiredArgsConstructor은 final 필드에 주입__DI)

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto save(BookSaveReqDto dto){
       Book bookPS = bookRepository.save(dto.toEntity());
       return new BookRespDto().toDto(bookPS);
    }

    //2. 책 목록보기

    //3. 책한건보기

    //4. 책삭제

    //5. 책수정
}
