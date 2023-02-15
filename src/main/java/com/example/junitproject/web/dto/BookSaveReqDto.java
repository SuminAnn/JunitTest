package com.example.junitproject.web.dto;

import com.example.junitproject.domain.Book;

import lombok.Setter;

@Setter //Controller에서 호출되면서 Dto에 값이 채워진다
public class BookSaveReqDto {
    private String title;
    private String content;

    public Book toEntity(){
        return Book.builder()
        .title(title)
        .author(content)
        .build();
    }
}
/*
  클라이언트 title, author -> Controller -> BookSaveReqDto -> Service -> Book Entity 변환 -> BookRepository.save(book)
 */
