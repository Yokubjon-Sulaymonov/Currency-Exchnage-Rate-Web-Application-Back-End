package com.example.exchangerate.controller.email;

import com.example.exchangerate.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("exchange/rates/support/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("{emailFrom}/{name}/{body}")
    public void sendEmail(@PathVariable("emailFrom") String emailFrom,
                          @PathVariable("name") String name,
                          @PathVariable("body") String body) {
        log.info("Attempting to send email from {}", emailFrom);
        emailService.sendEmail(emailFrom, name, body);
        log.info("Successfully sent email from {}", emailFrom);
    }
}
