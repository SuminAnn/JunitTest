package com.example.junitproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.util.MailSender;
import com.example.junitproject.web.dto.BookRespDto;
import com.example.junitproject.web.dto.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository; //final은 객체 생성 시점에 값이 들어와야한다(@RequiredArgsConstructor은 final 필드에 주입__DI)
    private final MailSender mailSender;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto save(BookSaveReqDto dto){
       Book bookPS = bookRepository.save(dto.toEntity());
       if(bookPS != null){
        if(!mailSender.send()){
            throw new RuntimeException("메일이 전송되지 않았습니다");
        }
       }
       return new BookRespDto().toDto(bookPS); //연속화된 객체를 끊어내기 위해서 Book이 아닌 dto를 return
    }

    //2. 책 목록보기
    public List<BookRespDto> selectList(){
        return bookRepository.findAll().stream()
        .map(new BookRespDto()::toDto)
        .collect(Collectors.toList()); // bookRepository.findAll()을 return하는 경우 Book타입으로 return이 되기때문에 dto로 변경해서 return을 해줘야한다
    } 

    //3. 책한건보기
    public BookRespDto selectOne(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            return new BookRespDto().toDto(bookOP.get());
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    //4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void delect(Long id){
        bookRepository.deleteById(id);
    }

    //5. 책수정
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(Long id, BookSaveReqDto dto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }
}

/*
-스프링 구동 순서
 클라이언트     ->      dispatcher servelet      ->      controller      ->      service     ->      Repository      ->      persistence context     ->      DB
            (클라이언트 요청 주소를 찾는다)   (dto를 받아 파싱, 유효성 체크 등)  (dto -> book으로 변경)  (저장 메서드 실행)         (체크)                      (저장)

                                    트랜잭션은 controller -> service로 넘어갈때 시작해서 service -> controller로 돌아올때 종료된다(werite X)
                        DB 세션은 controller에서 DS로 넘어갈때 종료(세션 종료 전까지 Select가능)

 메세지 컨버터 : getter로 응답

 Stream -> filter -> map -> collect
 */
