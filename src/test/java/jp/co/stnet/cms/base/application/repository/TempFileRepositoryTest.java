package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.TempFile;
import jp.co.stnet.cms.base.domain.model.mbg.TempFileExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TempFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
@Transactional
class TempFileRepositoryTest extends AbstractRepositoryStringIdTest<TempFile, TempFileExample, String> {

    @Autowired
    TempFileRepository target;

    @Autowired
    TempFileMapper mapper;


    @Override
    AbstractRepository<TempFile, TempFileExample, String> target() {
        return target;
    }

    @Override
    MapperInterface<TempFile, TempFileExample, String> mapper() {
        return mapper;
    }

    @Override
    TempFile createEntity(String id) {
        return TempFile.builder()
                .id(rightPad("id:" + id, 255, "0"))
                .body("aaa".getBytes(StandardCharsets.UTF_8))
                .originalName(rightPad("originalName:" + id, 255, "0"))
                .build();
    }

    @Override
    TempFileExample newExample() {
        return new TempFileExample();
    }

    @Override
    void setOverflowValue(TempFile entity) {
        entity.setId(entity.getPrimaryKey() + "a");
    }

    @Override
    void changeField(TempFile entity) {
        entity.setOriginalName("change");
    }

    @Override
    void fixVersion(TempFile entity) {
        // 該当なし
    }

    @Override
    void fixVersionList(List<TempFile> entities) {
        // 該当なし
    }

    @Override
    void setFindByCondition(TempFileExample example) {
        example.or().andIdEqualTo(createEntity("10").getPrimaryKey());
    }

    @Override
    void setNotFindByCondition(TempFileExample example) {
        example.or().andIdEqualTo(createEntity("not exists").getPrimaryKey());
    }

    @Override
    void setOrderByClause(TempFileExample example) {
        example.setOrderByClause("id desc");
    }
}