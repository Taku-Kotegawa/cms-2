package jp.co.stnet.cms.base.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PasswordReissueMailServiceImpl implements PasswordReissueMailService {

    private final JavaMailSender mailSender;
    @Qualifier("passwordReissueMessage")
    private final SimpleMailMessage templateMessage;


    @Override
    public void send(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setTo(to);
        message.setText(text);
        mailSender.send(message);
    }

}
