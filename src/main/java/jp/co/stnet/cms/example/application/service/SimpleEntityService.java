package jp.co.stnet.cms.example.application.service;

import jp.co.stnet.cms.base.application.service.NodeIService;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import jp.co.stnet.cms.example.domain.model.mbg.TSimpleEntityExample;

public interface SimpleEntityService extends NodeIService<SimpleEntity, TSimpleEntityExample, Long> {

//    Page<SimpleEntity> findPageByInput(DataTablesInput input);

}
