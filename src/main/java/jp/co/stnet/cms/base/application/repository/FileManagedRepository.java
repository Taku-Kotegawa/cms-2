package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.domain.model.mbg.FileManagedExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FileManagedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class FileManagedRepository extends AbstractRepository<FileManaged, FileManagedExample, String> {

    private final FileManagedMapper mapper;

    @Override
    MapperInterface<FileManaged, FileManagedExample, String> mapper() {
        return mapper;
    }

    @Override
    FileManagedExample example() {
        return new FileManagedExample();
    }

    public List<FileManaged> findAllByCreatedDateLessThanAndStatus(LocalDateTime deleteTo, String status) {
        var example = new FileManagedExample();
        example.or()
                .andCreatedDateLessThan(deleteTo)
                .andStatusEqualTo(status);
        return mapper.selectByExample(example);
    }

}
