package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissue;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissueExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissueKey;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfo;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FailedPasswordReissueMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PasswordReissueInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
@Transactional
class FailedPasswordReissueRepositoryTest extends AbstractRepositoryStringIdTest<FailedPasswordReissue, FailedPasswordReissueExample, FailedPasswordReissueKey> {

    @Autowired
    FailedPasswordReissueRepository target;

    @Autowired
    FailedPasswordReissueMapper mapper;

    @Autowired
    PasswordReissueInfoMapper passwordReissueInfoMapper;

    @BeforeEach
    void setUp() {
        mapper.deleteByExample(null);
        passwordReissueInfoMapper.deleteByExample(null);
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("01"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("02"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("03"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("04"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("05"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("06"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("07"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("08"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("09"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("10"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("1111"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("1112"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("1113"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("1114"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("11"));
        passwordReissueInfoMapper.insert(createPasswordReissueInfo("23"));
    }

    PasswordReissueInfo createPasswordReissueInfo(String id) {
        return PasswordReissueInfo.builder()
                .token(rightPad("token:" + id, 128, "0"))
                .username(rightPad("username:" + id, 128, "0"))
                .secret(rightPad("secret:" + id, 88, "0"))
                .expiryDate(LocalDateTime.of(2222, 2, 2, 2, 2, 2, 999999000))
                .build();
    }


    @Override
    AbstractRepository<FailedPasswordReissue, FailedPasswordReissueExample, FailedPasswordReissueKey> target() {
        return target;
    }

    @Override
    MapperInterface<FailedPasswordReissue, FailedPasswordReissueExample, FailedPasswordReissueKey> mapper() {
        return mapper;
    }

    @Override
    FailedPasswordReissue createEntity(String id) {
        return FailedPasswordReissue.builder()
                .token(rightPad("token:" + id, 128, "0"))
                .attemptDate(LocalDateTime.of(1111, 1, 1, 2, 2, 2, 999888000))
                .dummy("X")
                .build();
    }

    @Override
    FailedPasswordReissueExample newExample() {
        return new FailedPasswordReissueExample();
    }

    @Override
    void setOverflowValue(FailedPasswordReissue entity) {
        entity.setToken(entity.getToken() + "a");
    }

    @Override
    void changeField(FailedPasswordReissue entity) {
        entity.setAttemptDate(LocalDateTime.of(1, 1, 1, 2, 2, 2));
    }

    @Override
    void fixVersion(FailedPasswordReissue entity) {
        // なし
    }

    @Override
    void fixVersionList(List<FailedPasswordReissue> entities) {
        // なし
    }

    @Override
    void setFindByCondition(FailedPasswordReissueExample example) {
        example.or().andTokenEqualTo(createEntity("01").getToken());
    }

    @Override
    void setNotFindByCondition(FailedPasswordReissueExample example) {
        example.or().andTokenEqualTo(createEntity("not exists").getToken());
    }

    @Override
    void setOrderByClause(FailedPasswordReissueExample example) {
        example.setOrderByClause("token desc");
    }
}