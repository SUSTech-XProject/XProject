package com.ooad.xproject.service;

import java.util.List;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);

    void sendSimpleMail(String to, String subject, String content, String... cc);

    void sendMailToStudent(List<String> mailList, String subject, String content);
}
