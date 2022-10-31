package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FailedAuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class FailedAuthenticationRepository extends AbstractRepository<FailedAuthentication, FailedAuthenticationExample, FailedAuthenticationKey> {

    private final FailedAuthenticationMapper mapper;

    @Override
    MapperInterface<FailedAuthentication, FailedAuthenticationExample, FailedAuthenticationKey> mapper() {
        return mapper;
    }

    @Override
    FailedAuthenticationExample example() {
        return new FailedAuthenticationExample();
    }

    /**
     * ユーザ名を指定して削除
     *
     * @param username ユーザ名
     * @return 削除した件数
     */
    public int deleteByeUsername(String username) {
        var example = new FailedAuthenticationExample();
        example.or().andUsernameEqualTo(username);
        return mapper.deleteByExample(example);
    }

}
