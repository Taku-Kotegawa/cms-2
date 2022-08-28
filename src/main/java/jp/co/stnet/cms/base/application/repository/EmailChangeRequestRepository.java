package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.EmailChangeRequest;
import jp.co.stnet.cms.base.domain.model.mbg.EmailChangeRequestExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.EmailChangeRequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Component
public class EmailChangeRequestRepository extends AbstractRepository<EmailChangeRequest, EmailChangeRequestExample, String>{

    private final EmailChangeRequestMapper mapper;

    @Override
    MapperInterface<EmailChangeRequest, EmailChangeRequestExample, String> mapper() {
        return mapper;
    }
}
