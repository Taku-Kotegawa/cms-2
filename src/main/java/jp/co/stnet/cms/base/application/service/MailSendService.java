package jp.co.stnet.cms.base.application.service;



import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

public interface MailSendService {

    void sendMail(String to, String cc, String bcc, String messageTemplateCode, Map<String, String> params) throws MessagingException;

    List<MailSendHistory> getNewest();

}
