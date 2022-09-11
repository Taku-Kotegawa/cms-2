package jp.co.stnet.cms.base.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PasswordReissueMailServiceImpl implements PasswordReissueMailService {

    private final JavaMailSender mailSender;

    @Autowired
    @Named("passwordReissueMessage")
    SimpleMailMessage templateMessage;

    @Override
    public void send(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setTo(to);
        message.setText(text);
        mailSender.send(message);
    }

}
