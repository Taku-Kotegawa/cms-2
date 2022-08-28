package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.TempFile;
import jp.co.stnet.cms.base.domain.model.mbg.TempFileExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TempFileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Component
public class TempFileRepository extends AbstractRepository<TempFile, TempFileExample, String> {

    private final TempFileMapper mapper;

    @Override
    MapperInterface<TempFile, TempFileExample, String> mapper() {
        return mapper;
    }
}
