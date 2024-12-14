package ltweb.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("OTP Activation Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }
}
