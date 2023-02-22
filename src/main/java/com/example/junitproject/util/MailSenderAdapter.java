package com.example.junitproject.util;

import org.springframework.stereotype.Component;

// 추후에 Mail 클래스가 완성되면 코드를 완성
// @Component  //Ioc 컨테이너에 singleton관리를 위해 같은타입이 두개이상 올라갈수 없다
public class MailSenderAdapter implements MailSender{

    // private Mail mail;

    // public MailSenderAdapter(){
    //     this.mail = new Mail();
    // }

    @Override
    public boolean send() {
        return true;
        // return mail.sendMail();
    }
    
}
