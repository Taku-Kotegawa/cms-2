package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.domain.model.mbg.FileManagedExample;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FileManagedMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Component
public class FileManagedRepository extends AbstractVersionRepository<FileManaged, FileManagedExample, Long> {

    private final FileManagedMapper mapper;

    @Override
    VersionMapperInterface<FileManaged, FileManagedExample, Long> mapper() {
        return mapper;
    }
}
