package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileManagedMapperTest {

    @Autowired
    FileManagedMapper target;

    @Test
    void countByExample() {
    }

    @Test
    void deleteByExample() {
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void insert() {

        var entity = FileManaged.builder()
                .version(1L)
                .uuid("b")
                .build();

        target.insert(entity);

    }

    @Test
    void insertSelective() {
    }

    @Test
    void selectByExampleWithRowbounds() {
    }

    @Test
    void selectByExample() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByExampleSelective() {
    }

    @Test
    void updateByExample() {
    }

    @Test
    void updateByPrimaryKeyAndVersionSelective() {
    }

    @Test
    void updateByPrimaryKeySelective() {
    }

    @Test
    void updateByPrimaryKeyAndVersion() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}