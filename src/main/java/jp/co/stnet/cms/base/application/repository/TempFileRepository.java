package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.TempFile;
import jp.co.stnet.cms.base.domain.model.mbg.TempFileExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TempFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class TempFileRepository extends AbstractRepository<TempFile, TempFileExample, String> {

    private final TempFileMapper mapper;

    @Override
    protected MapperInterface<TempFile, TempFileExample, String> mapper() {
        return mapper;
    }

    @Override
    protected TempFileExample example() {
        return new TempFileExample();
    }
}
