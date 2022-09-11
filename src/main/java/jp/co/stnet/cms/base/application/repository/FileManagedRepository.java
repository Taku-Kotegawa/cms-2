package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.domain.model.mbg.FileManagedExample;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FileManagedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Component
public class FileManagedRepository extends AbstractVersionRepository<FileManaged, FileManagedExample, Long> {

    private final FileManagedMapper mapper;

    @Override
    VersionMapperInterface<FileManaged, FileManagedExample, Long> mapper() {
        return mapper;
    }

    @Override
    FileManagedExample example() {
        return new FileManagedExample();
    }

//
//
//    public Optional<FileManaged> findById(Long id) {
//        var entity = mapper.selectByPrimaryKey(id);
//        return Optional.ofNullable(entity);
//    }

    public Optional<FileManaged> findByUuid(String uuid) {
        var example = new FileManagedExample();
        example.or().andUuidEqualTo(uuid);
        var entities = mapper.selectByExample(example);
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(entities.get(0));
        }
    }

//    public FileManaged register(FileManaged fileManaged) {
//        mapper.insert(fileManaged);
//        return mapper.selectByPrimaryKey(fileManaged.getId());
//    }

    public List<FileManaged> findAllByCreatedDateLessThanAndStatus(LocalDateTime deleteTo, String codeValue) {
        return null;
    }

    public Optional<FileManaged> findByUuidAndStatus(String uuid, String codeValue) {
        return null;
    }

}
