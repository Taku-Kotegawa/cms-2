package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.EmailChangeRequestRepository;
import jp.co.stnet.cms.base.application.repository.FailedEmailChangeRequestRepository;
import jp.co.stnet.cms.base.domain.model.mbg.EmailChangeRequest;
import jp.co.stnet.cms.base.domain.model.mbg.FailedEmailChangeRequest;
import jp.co.stnet.cms.common.datetime.DateTimeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jp.co.stnet.cms.common.message.MessageKeys.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class EmailChangeServiceImpl implements EmailChangeService {

    private final EmailChangeRequestRepository emailChangeRequestRepository;
    private final FailedEmailChangeRequestRepository failedEmailChangeRequestRepository;
    private final AccountService accountService;

    private final PasswordGenerator passwordGenerator;
    private final JavaMailSender mailSender;
    private final DateTimeFactory dateFactory;

    @Resource(name = "passwordGenerationRules")
    List<CharacterRule> passwordGenerationRules;

    @Value("${security.tokenLifeTimeSeconds}")
    int tokenLifeTimeSeconds;

    @Value("${mail.from}")
    String mailFrom;

    @Value("${security.tokenValidityThreshold}")
    int tokenValidityThreshold;

    @Override
    @Transactional(rollbackFor = MailException.class)
    public String createAndSendMailChangeRequest(String username, String mail) {

        // 暗証番号を生成
        String rowSecret = passwordGenerator.generatePassword(10, passwordGenerationRules);

        // トークンを生成
        String token = UUID.randomUUID().toString();

        // 暗証番号の有効期限
        LocalDateTime expiryDate = dateFactory.getNow().plusSeconds(tokenLifeTimeSeconds);

        // 新しいメールアドレスと暗証番号を記録
        emailChangeRequestRepository.register(
                EmailChangeRequest.builder()
                        .token(token)
                        .username(username)
                        .secret(rowSecret)
                        .newMail(mail)
                        .expiryDate(expiryDate)
                        .build()
        );

        // 暗証番号を新しいメールアドレスに送信
        sendMail(mail, rowSecret);

        // トークンを返す
        return token;
    }

    private void sendMail(String to, String secret) throws MailException {
        mailSender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                        StandardCharsets.UTF_8.name()); // (3)
                helper.setFrom(mailFrom); // (4)
                helper.setTo(to); // (5)
                helper.setSubject("メールアドレス変更の確認"); // (6)
                String text = "暗証番号: {secret}";
                text = text.replace("{secret}", secret);
                helper.setText(text, false); // (7)
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public EmailChangeRequest findOne(String token) {

        // トークンの存在チェック
        var emailChangeRequest = emailChangeRequestRepository.findById(token)
                .orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(E_SL_MC_5002, token)));

        // 有効期限チェック
        if (dateFactory.getNow().isAfter(emailChangeRequest.getExpiryDate())) {
            throw new BusinessException(ResultMessages.error().add(E_SL_MC_5004, token));
        }

        // 失敗回数チェック
        long count = failedEmailChangeRequestRepository.countByToken(token);
        if (count >= tokenValidityThreshold) {
            throw new BusinessException(ResultMessages.error().add(E_SL_PR_5004));
        }

        return emailChangeRequest;
    }

    @Override
    public void changeEmail(String token, String secret) {
        EmailChangeRequest emailChangeRequest = findOne(token);
        if (!Objects.equals(emailChangeRequest.getSecret(), secret)) {
            fail(token);
            throw new BusinessException(ResultMessages.error().add(E_SL_MC_5003));
        }
        failedEmailChangeRequestRepository.deleteByToken(token);
        emailChangeRequestRepository.deleteById(token);
        accountService.updateEmail(emailChangeRequest.getUsername(), emailChangeRequest.getNewMail());
    }

    @Override
    public void removeExpired(LocalDateTime date) {

        // DELETE FROM failedPasswordReissue WHERE expiry_date < #{date}
        failedEmailChangeRequestRepository.deleteByAttemptDateLessThan(date);

        // DELETE FROM passwordReissueInfo WHERE expiry_date < #{date}
        emailChangeRequestRepository.deleteByExpiryDateLessThan(date);

    }

    @Override
    public FailedEmailChangeRequest fail(String token) {
        var row = new FailedEmailChangeRequest();
        row.setToken(token);
        row.setAttemptDate(dateFactory.getNow());
        return failedEmailChangeRequestRepository.register(row);
    }
}
