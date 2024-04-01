package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;

import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Map;

public interface MailSendService {

    /**
     * メールを送信する
     *
     * @param to
     * @param cc
     * @param bcc
     * @param messageTemplateCode
     * @param params
     * @throws MessagingException
     */
    void sendMail(String to, String cc, String bcc, String messageTemplateCode, Map<String, String> params) throws MessagingException;

    /**
     * ???
     *
     * @return
     */
    List<MailSendHistory> getNewest();

}
