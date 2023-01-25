package com.example.exchangerate.service.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    /** This method is used to send a simple email **/
    void sendEmail(String emailFrom, String subject, String body);
}
