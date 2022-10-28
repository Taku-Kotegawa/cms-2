package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Mapper
public interface AccountQueryMapper {

    Account findById(String id);

    List<Account> findPageByInput(@Param("dataTablesInput") DataTablesInput dataTablesInput, @Param("pageable") Pageable pageable);

    long countByInput(@Param("dataTablesInput") DataTablesInput dataTablesInput);

}
