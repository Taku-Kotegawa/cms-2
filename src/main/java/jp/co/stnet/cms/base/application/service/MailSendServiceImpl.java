package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.MailSendHistoryRepository;
import jp.co.stnet.cms.base.domain.enums.VariableType;
import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.infrastructure.mapper.MailSendHistoryQueryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MailSendServiceImpl implements MailSendService {

    private static final String VARIABLE_TYPE_MESSAGE_TEMPLATE = VariableType.MESSAGE_TEMPLATE.name();

    private final VariableService variableService;
    private final JavaMailSender mailSender;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    private final MailSendHistoryQueryMapper mailSendHistoryMapper;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String cc, String bcc, String messageTemplateCode, Map<String, String> params) throws MessagingException {

        String subject = "";
        String body = "";

        List<Variable> variables = variableService.findAllByTypeAndCode(VARIABLE_TYPE_MESSAGE_TEMPLATE, messageTemplateCode);
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
            String to, String cc, String bcc, String subject, String body, Map<String, String> params) {

        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                var helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
                helper.setFrom(from);
                helper.setTo(to);
                helper.setCc(cc);
                helper.setBcc(bcc);
                helper.setSubject(subject);
                var text = body;
                for (var entry : params.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                }
                helper.setText(text, true);
            }
        };
    }
}
