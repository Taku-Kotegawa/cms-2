package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfo;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfoExample;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PasswordReissueInfoRepositoryTest extends AbstractRepositoryStringIdTest<PasswordReissueInfo, PasswordReissueInfoExample, String> {

    @Autowired
    PasswordReissueInfoRepository target;

    @Autowired
    PasswordReissueInfoMapper mapper;

    @Autowired
    FailedPasswordReissueMapper failedPasswordReissueMapper;

    @BeforeEach
    void setUp() {
        failedPasswordReissueMapper.deleteByExample(null);
    }

    @Override
    AbstractRepository<PasswordReissueInfo, PasswordReissueInfoExample, String> target() {
        return target;
    }

    @Override
    MapperInterface<PasswordReissueInfo, PasswordReissueInfoExample, String> mapper() {
        return mapper;
    }

    @Override
    PasswordReissueInfo createEntity(String id) {
        return PasswordReissueInfo.builder()
                .token(rightPad("token:" + id, 128, "0"))
                .username(rightPad("username:" + id, 128, "0"))
                .secret(rightPad("secret:" + id, 88, "0"))
                .expiryDate(LocalDateTime.of(2222,2,2,2,2,2, 999999000))
                .build();
    }

    @Override
    PasswordReissueInfoExample newExample() {
        return new PasswordReissueInfoExample();
    }

    @Override
    void setOverflowValue(PasswordReissueInfo entity) {
        entity.setToken(entity.getToken() + "a");
    }

    @Override
    void changeField(PasswordReissueInfo entity) {
        entity.setUsername("change");
    }

    @Override
    void fixVersion(PasswordReissueInfo entity) {

    }

    @Override
    void fixVersionList(List<PasswordReissueInfo> entities) {

    }

    @Override
    void setFindByCondition(PasswordReissueInfoExample example) {
        example.or().andTokenEqualTo(createEntity("01").getToken());
    }

    @Override
    void setNotFindByCondition(PasswordReissueInfoExample example) {
        example.or().andTokenEqualTo(createEntity("not exists").getToken());
    }

    @Override
    void setOrderByClause(PasswordReissueInfoExample example) {
        example.setOrderByClause("token desc");
    }

}