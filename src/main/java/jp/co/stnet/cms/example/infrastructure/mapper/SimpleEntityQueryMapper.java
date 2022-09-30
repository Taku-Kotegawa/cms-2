package jp.co.stnet.cms.example.infrastructure.mapper;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface SimpleEntityQueryMapper {

    List<SimpleEntity> findByInput(@Param("dataTablesInput") DataTablesInput input);

    Long countByInput(@Param("dataTablesInput") DataTablesInput input);

}
