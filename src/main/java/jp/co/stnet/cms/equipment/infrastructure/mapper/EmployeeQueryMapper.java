package jp.co.stnet.cms.equipment.infrastructure.mapper;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface EmployeeQueryMapper {

    List<VEmployee> findPageByInput(@Param("dataTablesInput") DataTablesInput dataTablesInput, @Param("pageable") Pageable pageable);

    long countByInput(@Param("dataTablesInput") DataTablesInput dataTablesInput);

}
