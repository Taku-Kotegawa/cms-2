package jp.co.stnet.cms.example.application.service;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.application.service.AbstractNodeService;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.domain.model.mbg.VariableExample;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.example.application.repository.SimpleEntityRepository;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import jp.co.stnet.cms.example.domain.model.mbg.TSimpleEntityExample;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SimpleEntityServiceImpl extends AbstractNodeService<SimpleEntity, TSimpleEntityExample, Long> implements SimpleEntityService {

    private final SimpleEntityRepository simpleEntityRepository;

    @Override
    public Page<SimpleEntity> findPageByInput(DataTablesInput input) {
        return simpleEntityRepository.findPageByInput(input);
    }

//    @Override
//    public Page<SimpleEntity> findPageByExampleWithRowBounds(Example example, RowBounds rowBounds) {
//        return null;
//    }

    @Override
    protected VersionRepositoryInterface<SimpleEntity, TSimpleEntityExample, Long> repository() {
        return simpleEntityRepository;
    }
}
