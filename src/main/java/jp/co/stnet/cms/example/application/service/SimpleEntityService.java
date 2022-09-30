package jp.co.stnet.cms.example.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SimpleEntityService {

    Page<SimpleEntity> findPageByInput(DataTablesInput input);

}
