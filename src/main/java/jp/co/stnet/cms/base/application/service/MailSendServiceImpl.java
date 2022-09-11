package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.MailSendHistoryRepository;

import jp.co.stnet.cms.base.domain.enums.VariableType;
import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;

import jp.co.stnet.cms.base.infrastructure.mapper.MailSendHistoryQueryMapper;


import jp.co.stnet.cms.common.datetime.DateTimeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sound.midi.Receiver;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class MailSendServiceImpl implements MailSendService {

    private static final String type = VariableType.MESSAGE_TEMPLATE.name();
    @Autowired
    VariableService variableService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    MailSendHistoryRepository mailSendHistoryRepository;

    @Autowired
    MailSendHistoryQueryMapper mailSendHistoryMapper;

    @Autowired
    DateTimeFactory dateTimeFactory;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String cc, String bcc, String messageTemplateCode, Map<String, String> params) throws MessagingException {

        String subject = "";
        String body = "";

        List<Variable> variables = variableService.findAllByTypeAndCode(type, messageTemplateCode);
        if (!variables.isEmpty()) {
            subject = variables.get(0).getValue1();
            body = variables.get(0).getTextarea();

        }
        mailSender.send(getMimePreparators(to, cc, bcc, subject, body, params));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailSendHistory> getNewest() {
        return mailSendHistoryMapper.selectNewest();
    }


    private MimeMessagePreparator[] getMimePreparators(String to, String cc, String bcc, String subject, String body, Map<String, String> params) throws MessagingException {
        List<MimeMessagePreparator> list = new ArrayList<>();

            list.add(getOneMimeMessagePreparator(to, cc, bcc, subject, body, params));
            mailSendHistoryRepository.save(
                    MailSendHistory.builder()
                            .subject(subject)
                            .body(body)
                            .emailTo(to)
                            .emailCc(cc)
                            .emailBcc(bcc)
                            .build());
        return list.toArray(new MimeMessagePreparator[list.size()]);
    }


    private MimeMessagePreparator getOneMimeMessagePreparator(
            String to, String cc, String bcc, String subject, String body, Map<String, String> params) throws MessagingException {

        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
                helper.setFrom(from);
                helper.setTo(to);
                helper.setCc(cc);
                helper.setBcc(bcc);
                helper.setSubject(subject);
                String text = body;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                }
                helper.setText(text, true);
            }
        };

    }


}
