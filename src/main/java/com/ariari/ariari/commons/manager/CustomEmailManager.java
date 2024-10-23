package com.ariari.ariari.commons.manager;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomEmailManager {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final AuthCodeManager authCodeManager;

    // 이메일로 text 전송
    public void makeAuthCodeMail(String toEmail) {
        String authCode = authCodeManager.createAuthCode(toEmail); // 암호생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("mailsystem9983@naver.com");
        message.setSubject("[Ariari] 회원가입 인증번호입니다.");
        message.setText("이메일 인증 코드 : " + authCode);
        try{
            mailSender.send(message);
        }
        catch (MailException e){
            throw new RuntimeException(); // 익셉션 추가 필요
        }
    }

    // 이메일 HTML 객체 생성 후 전송
    public void makeAuthCodeTemplateMail(String toEmail) {
        try {
            String authCode = authCodeManager.createAuthCode(toEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom("mailsystem9983@naver.com");
            helper.setSubject("[Ariari] 회원가입 인증번호입니다.");

            Context context = new Context();
            context.setVariable("authCode", authCode);

            String html = templateEngine.process("emailTemplate", context);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(); // 익셉션 추가 필요
        }
    }
}
