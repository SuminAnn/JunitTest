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

  1. 메서드는 하나의 기능(책임 분리)
  2. 각 메서드에 해당하는 테스트 코드 작성(유지보수 편리)
  3. 시간 단축
 */
