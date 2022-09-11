package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.EmailChangeRequest;
import jp.co.stnet.cms.base.domain.model.mbg.EmailChangeRequestExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.EmailChangeRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Component
public class EmailChangeRequestRepository extends AbstractRepository<EmailChangeRequest, EmailChangeRequestExample, String> {

    private final EmailChangeRequestMapper mapper;

    @Override
    MapperInterface<EmailChangeRequest, EmailChangeRequestExample, String> mapper() {
        return mapper;
    }

    @Override
    EmailChangeRequestExample example() {
        return new EmailChangeRequestExample();
    }

    /**
     * 有効期限を迎えたデータ削除
     *
     * @param expireDate 有効期限(日付)
     * @return 削除した件数
     */
    public int deleteByExpiryDateLessThan(LocalDateTime expireDate) {
        var example = new EmailChangeRequestExample();
        example.or().andExpiryDateLessThan(expireDate);
        return mapper.deleteByExample(example);
    }

}
