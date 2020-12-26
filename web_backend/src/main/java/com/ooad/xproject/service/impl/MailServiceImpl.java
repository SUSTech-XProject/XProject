package com.ooad.xproject.service.impl;

import com.ooad.xproject.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Async
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")       // username is from email address
    private String from;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        if (to == null || "".equals(to)) {
            System.out.println("Null mail");
            return;
        }
        if (isFakeMail(to)) {
            System.out.println("Fake mail");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
        System.out.println("Send finished");
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }

        System.out.println("Send finished");
    }

    @Override
    public void sendMailToStudent(List<String> mailList, String subject, String content) {
        int successCnt = 0;
        for (String to : mailList) {
            sendSimpleMail(to, subject, content);
        }
        System.out.println("Send to all, successCnt = " + successCnt);
    }

    private boolean isFakeMail(String mail) {
        return mail.contains(".fake.");
    }
}
