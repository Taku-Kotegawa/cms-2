package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.FailedPasswordReissueRepository;
import jp.co.stnet.cms.base.application.repository.PasswordReissueInfoRepository;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfo;
import jp.co.stnet.cms.common.datetime.DateTimeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jp.co.stnet.cms.common.message.MessageKeys.*;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PasswordReissueServiceImpl implements PasswordReissueService {

    private final PasswordReissueFailureService passwordReissueFailureService;
    private final PasswordReissueMailService mailSharedService;
    private final PasswordReissueInfoRepository passwordReissueInfoRepository;
    private final FailedPasswordReissueRepository failedPasswordReissueRepository;
    private final AccountSharedService accountSharedService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final DateTimeFactory dateTimeFactory;

    @Resource(name = "passwordGenerationRules")
    List<CharacterRule> passwordGenerationRules;

    @Value("${security.tokenLifeTimeSeconds}")
    int tokenLifeTimeSeconds;

    @Value("${app.host}")
    String host;

    @Value("${app.port}")
    String port;

    @Value("${app.contextPath}")
    String contextPath;

    @Value("${app.passwordReissueProtocol}")
    String protocol;

    @Value("${security.tokenValidityThreshold}")
    int tokenValidityThreshold;


    @Override
    public String createAndSendReissueInfo(String username) {

        String rowSecret = passwordGenerator.generatePassword(10, passwordGenerationRules);

        if (!accountSharedService.exists(username)) {
            return rowSecret;
        }

        Account account = accountSharedService.findOne(username);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = dateTimeFactory.getNow().plusSeconds(tokenLifeTimeSeconds);

        PasswordReissueInfo info = passwordReissueInfoRepository.register(
                PasswordReissueInfo.builder()
                        .username(username)
                        .token(token)
                        .secret(passwordEncoder.encode(rowSecret))
                        .expiryDate(expiryDate)
                        .build()
        );

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder.scheme(protocol).host(host).port(port).path(contextPath)
                .pathSegment("reissue").pathSegment("resetpassword")
                .queryParam("form").queryParam("token", info.getToken());
        String passwordResetUrl = uriBuilder.build().toString();

        mailSharedService.send(account.getEmail(), passwordResetUrl);
        return rowSecret;
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordReissueInfo findOne(String token) {
        var info = passwordReissueInfoRepository.findById(token)
                .orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(E_SL_PR_5002, token)));

        if (dateTimeFactory.getNow().isAfter(info.getExpiryDate())) {
            throw new BusinessException(ResultMessages.error().add(E_SL_PR_2001, token));
        }

        long count = failedPasswordReissueRepository.countByToken(token);
        if (count >= tokenValidityThreshold) {
            throw new BusinessException(ResultMessages.error().add(E_SL_PR_5004));
        }

        return info;
    }

    @Override
    public boolean resetPassword(String username, String token, String secret, String rawPassword) {
        PasswordReissueInfo info = this.findOne(token);
        if (!passwordEncoder.matches(secret, info.getSecret())) {
            passwordReissueFailureService.resetFailure(username, token);
            throw new BusinessException(ResultMessages.error().add(E_SL_PR_5003));
        }
        failedPasswordReissueRepository.deleteByToken(token);
        passwordReissueInfoRepository.deleteById(token);
        return accountSharedService.updatePassword(username, rawPassword);
    }

    @Override
    public boolean removeExpired(LocalDateTime date) {

        // DELETE FROM failedPasswordReissue WHERE expiry_date < #{date}
        failedPasswordReissueRepository.deleteByAttemptDateLessThan(date);

        // DELETE FROM passwordReissueInfo WHERE expiry_date < #{date}
        passwordReissueInfoRepository.deleteByExpiryDateLessThan(date);

        return true;
    }

}
