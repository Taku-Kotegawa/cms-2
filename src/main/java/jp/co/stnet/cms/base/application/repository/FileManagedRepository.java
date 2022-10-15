package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.enums.FileType;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.domain.model.mbg.FileManagedExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
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

    /**
     * 条件に合致したFileManagedを取得する
     *
     * @param uuid     uuid
     * @param username username
     * @return 検索結果
     */
    public Optional<FileManaged> findByIdAndCreatedBy(String uuid, String username) {
        var example = new FileManagedExample();
        example.or()
                .andUuidEqualTo(uuid)
                .andCreatedByEqualTo(username);
        var list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }


    /**
     * 条件に合致したFileManagedを取得する
     *
     * @param uuid     uuid
     * @param fileType fileType
     * @return 検索結果
     */
    public Optional<FileManaged> findByIdAndFileType(String uuid, FileType fileType) {
        var example = new FileManagedExample();
        example.or()
                .andUuidEqualTo(uuid)
                .andFileTypeEqualTo(fileType.getCodeValue());
        var list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    /**
     * 条件に合致したFileManagedを取得する
     *
     * @param uuid     uuid
     * @param fileType fileType
     * @param username username
     * @return 検索結果
     */
    public Optional<FileManaged> findByIdAndFileTypeAndCreatedBy(String uuid, FileType fileType, String username) {
        var example = new FileManagedExample();
        example.or()
                .andUuidEqualTo(uuid)
                .andFileTypeEqualTo(fileType.getCodeValue())
                .andCreatedByEqualTo(username);
        var list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

}
