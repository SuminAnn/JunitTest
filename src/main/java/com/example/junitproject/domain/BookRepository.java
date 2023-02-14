package com.example.junitproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
    
}
//JpaRepository를 상속받으면 @Repository는 생략이 가능하다