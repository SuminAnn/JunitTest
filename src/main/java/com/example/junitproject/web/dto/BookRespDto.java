package com.example.junitproject.web.dto;

import com.example.junitproject.domain.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookRespDto {
    private Long id;
    private String titile;
    private String author;

    public BookRespDto toDto(Book bookPS){
        this.id = bookPS.getId();
        this.titile = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
    
}
