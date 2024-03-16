package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.SuccessfulAuthenticationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
@Transactional
class SuccessfulAuthenticationRepositoryTest extends AbstractRepositoryStringIdTest<SuccessfulAuthentication, SuccessfulAuthenticationExample, SuccessfulAuthenticationKey> {

    @Autowired
    SuccessfulAuthenticationRepository target;

    @Autowired
    SuccessfulAuthenticationMapper mapper;

    @Override
    AbstractRepository<SuccessfulAuthentication, SuccessfulAuthenticationExample, SuccessfulAuthenticationKey> target() {
        return target;
    }

    @Override
    MapperInterface<SuccessfulAuthentication, SuccessfulAuthenticationExample, SuccessfulAuthenticationKey> mapper() {
        return mapper;
    }

    @Override
    SuccessfulAuthentication createEntity(String id) {
        return SuccessfulAuthentication.builder()
                .username(rightPad("usernaem:" + id, 128, "0"))
                .authenticationTimestamp(LocalDateTime.of(1111, 1, 1, 1, 1, 1, 999999000))
                .dummy("X")
                .build();
    }

    @Override
    SuccessfulAuthenticationExample newExample() {
        return new SuccessfulAuthenticationExample();
    }

    @Override
    void setOverflowValue(SuccessfulAuthentication entity) {
        entity.setUsername(entity.getUsername() + "a");

    }

    @Override
    void changeField(SuccessfulAuthentication entity) {
        entity.setAuthenticationTimestamp(LocalDateTime.of(2222,2,2,2,2,2));

    }

    @Override
    void fixVersion(SuccessfulAuthentication entity) {
        // 該当なし
    }

    @Override
    void fixVersionList(List<SuccessfulAuthentication> entities) {
        // 該当なし
    }

    @Override
    void setFindByCondition(SuccessfulAuthenticationExample example) {
        example.or().andUsernameEqualTo(createEntity("01").getUsername());

    }

    @Override
    void setNotFindByCondition(SuccessfulAuthenticationExample example) {
        example.or().andUsernameEqualTo(createEntity("99").getUsername());
    }

    @Override
    void setOrderByClause(SuccessfulAuthenticationExample example) {
        example.setOrderByClause("username desc");
    }
}