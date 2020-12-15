package com.hero.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;

    //@Autowired
    //private EmailRepository emailRepository;
    //
    //@Autowired
    //private UserRepository userRepository;

    //@Async("taskExecutor")
    public void sendVerificationEmail(Long userId) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //EmailVerifier emailVerifier = getEmailVerifierByUserId(userId);
            //String email = emailVerifier.getEmail();
            //String token = emailVerifier.getToken();
            //String text = "请点击链接验证邮箱: https://此处输入你的主服务器地址/api/v1/user/email_verification?token=" + token;
            String text = "Email Verification\n" +
                    "Hero Inventory needs to confirm your email address is valid.\n" +
                    "Please click the link below to confirm you received this mail.\n" +
                    "https://localhost:3000/api/v1/user/email_verification?token=\n";
            simpleMailMessage.setTo("2012.ewig@gmail.com");
            simpleMailMessage.setSubject("Hero Inventory Email Verification");
            simpleMailMessage.setFrom("inventory.hero@gmail.com");
            simpleMailMessage.setText(text);
            mailSender.send(simpleMailMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //public void addEmailVerifier(int userId, String email, String token) {
    //    emailRepository.addEmailVerifier(userId, email, token);
    //}
    //
    //public EmailVerifier getEmailVerifierByUserId(int userId) {
    //    return emailRepository.getEmailVerifierByUserId(userId);
    //}
    //
    //public EmailVerifier getEmailVerifierByToken(String token) {
    //    EmailVerifier emailVerifier = emailRepository.getEmailVerifierByToken(token);
    //    return emailVerifier;
    //}
    //
    //public void setUserEmailVerified(int userId) {
    //    userRepository.setUserEmailVerified(userId);
    //}
}
