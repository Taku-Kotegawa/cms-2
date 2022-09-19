package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Mapper
public interface VariableQueryMapper {

    List<Variable> findPageByInput(DataTablesInput dataTablesInput, Pageable pageable);

}
