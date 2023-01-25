package com.example.exchangerate.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String emailFrom, String name, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("currex.contact@gmail.com");
        message.setSubject(name);
        message.setText("Email from " + emailFrom + "\n\n" + body);
        mailSender.send(message);
    }
}
