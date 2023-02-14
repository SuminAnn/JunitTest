package com.example.junitproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //빈 생성자
@Getter
@Entity // 테이블 자동으로 생성
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY) //1씩 자동증가(Primary Key)
    @Id
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 20, nullable = false)
    private String author;
   
    @Builder //Builder는 객체를 생성할때 원하는 것들과 순서 상관없이 생성가능
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

   

}
