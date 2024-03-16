package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.enums.FileStatus;
import jp.co.stnet.cms.base.domain.enums.FileType;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.domain.model.mbg.FileManagedExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FileManagedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
@Transactional
public class FileManagedRepositoryTest extends AbstractRepositoryStringIdTest<FileManaged, FileManagedExample, String> {

    @Autowired
    FileManagedRepository repository;

    @Autowired
    FileManagedMapper mapper;

    @Override
    AbstractRepository<FileManaged, FileManagedExample, String> target() {
        return repository;
    }

    @Override
    MapperInterface<FileManaged, FileManagedExample, String> mapper() {
        return mapper;
    }

    @Override
    FileManaged createEntity(String id) {
        return FileManaged.builder()
                .uuid(rightPad("uuid:" + id, 255, "0"))
                .fileType(FileType.DEFAULT.name())
                .originalFilename(rightPad("originalFilename:" + id, 255, "0"))
                .fileMime(rightPad("fileMime:" + id, 255, "0"))
                .fileSize(Long.MAX_VALUE)
                .status(FileStatus.TEMPORARY.getCodeValue())
                .uri(rightPad("fileMime:" + id, 255, "0"))
                .createdBy(rightPad("createdBy:" + id, 255, "0"))
                .createdDate(LocalDateTime.of(2020, 1, 1, 1, 1, 1))
                .lastModifiedBy(rightPad("lastModifiedBy:" + id, 255, "0"))
                .lastModifiedDate(LocalDateTime.of(2022, 2, 2, 2, 2, 2, 2))
                .build();
    }

    @Override
    FileManagedExample newExample() {
        return new FileManagedExample();
    }

    @Override
    void setOverflowValue(FileManaged entity) {
        entity.setUuid(entity.getUuid() + "a");
    }

    @Override
    void changeField(FileManaged entity) {
        entity.setStatus(FileStatus.PERMANENT.getCodeValue());
    }

    @Override
    void fixVersion(FileManaged entity) {

    }

    @Override
    void fixVersionList(List<FileManaged> entities) {

    }

    @Override
    void setFindByCondition(FileManagedExample example) {
        example.or().andUuidEqualTo(createEntity("01").getUuid());
    }

    @Override
    void setNotFindByCondition(FileManagedExample example) {
        example.or().andUuidEqualTo("NOT EXIST");
    }

    @Override
    void setOrderByClause(FileManagedExample example) {
        example.setOrderByClause("uuid desc");
    }


}
