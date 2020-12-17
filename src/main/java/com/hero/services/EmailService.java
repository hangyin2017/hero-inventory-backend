package com.hero.services;

import com.hero.entities.EmailVerifier;
import com.hero.repositories.EmailVerifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${application.config.host}")
    private String host;

    @Value("${application.email.emailHost}")
    private String emailHost;

    private final MailSender mailSender;
    private final EmailVerifierRepository emailVerifierRepository;

    public void sendVerificationEmail(Long userId, String subject, String text) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            EmailVerifier emailVerifier = emailVerifierRepository.findByUserId(userId);
            String email = emailVerifier.getEmail();
            String token = emailVerifier.getToken();
            String textWithToken = text + token;
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setFrom(emailHost);
            simpleMailMessage.setText(textWithToken);
            mailSender.send(simpleMailMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmailVerifier(Long userId, String email, String token) {
        EmailVerifier emailVerifier = new EmailVerifier(userId, email, token);
        emailVerifierRepository.save(emailVerifier);
    }

    public EmailVerifier getEmailVerifierByUserId(Long userId) {
        EmailVerifier emailVerifier = emailVerifierRepository.findByUserId(userId);
        if (emailVerifier == null) { throw new RuntimeException("Invalid token"); }
        return emailVerifier;
    }

    public EmailVerifier getEmailVerifierByToken(String token) {
        EmailVerifier emailVerifier = emailVerifierRepository.findByToken(token);
        if (emailVerifier == null) { throw new RuntimeException("Invalid token"); }
        return emailVerifier;
    }

    public void deleteEmailVerifier(EmailVerifier emailVerifier) {
        emailVerifierRepository.delete(emailVerifier);
    }

    public void deleteEmailVerifierByUserId(Long userId) {
        emailVerifierRepository.deleteByUserId(userId);
    }

    public void sendSignUpVerificationEmail(Long id) {
        String subject = "Hero Inventory Email Verification";
        String text = "Email Verification\n" +
                "Hero Inventory needs to confirm your email address is valid.\n" +
                "Please click the link below to confirm you received this mail.\n" +
                host + "auth/email_verification?token=";

        sendVerificationEmail(id, subject, text);
    }

    public void sendResetPasswordVerificationEmail(Long id) {
        String subject = "Hero Inventory Reset Password";
        String text = "Reset Password\n" +
                "You recently requested to reset your password for your Hero Inventory Account.\n" +
                "Please click the link below to reset.\n" +
                host + "auth/reset_password?token=";

        sendVerificationEmail(id, subject, text);
    }
}
