package com.hero.services;

import com.hero.entities.EmailVerifier;
import com.hero.repositories.EmailVerifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;

    private final EmailVerifierRepository emailVerifierRepository;
    //
    //@Autowired
    //private UserRepository userRepository;

    //@Async("taskExecutor")
    public void sendVerificationEmail(Long userId) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            EmailVerifier emailVerifier = emailVerifierRepository.findByUserId(userId);
            String email = emailVerifier.getEmail();
            String token = emailVerifier.getToken();
            String text = "Email Verification\n" +
                    "Hero Inventory needs to confirm your email address is valid.\n" +
                    "Please click the link below to confirm you received this mail.\n" +
                    "http://localhost:8080/api/v1/auth/email_verification?token=" + token;
            simpleMailMessage.setTo("2012.ewig@gmail.com");
            simpleMailMessage.setSubject("Hero Inventory Email Verification");
            simpleMailMessage.setFrom("inventory.hero@gmail.com");
            simpleMailMessage.setText(text);
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
        return emailVerifierRepository.findByUserId(userId);
    }

    public EmailVerifier getEmailVerifierByToken(String token) {
        return emailVerifierRepository.findByToken(token);
    }

    public void deleteEmailVerifier(EmailVerifier emailVerifier) {
        emailVerifierRepository.delete(emailVerifier);
    }
}
