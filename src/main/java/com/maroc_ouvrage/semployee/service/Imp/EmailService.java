package com.maroc_ouvrage.semployee.service.Imp;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAccountEmail(String to, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marocouvrageatelier@gmail.com");
        message.setTo(to);
        message.setSubject("Your Account Details");
        message.setText("Hello,\n\nYour account has been created.\nUsername: " + username + "\nPassword: " + password + "\n\nPlease change your password after first login.");

        mailSender.send(message);
    }
}
