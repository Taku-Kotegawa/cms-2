package jp.co.stnet.cms.example.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.example.application.repository.SimpleEntityRepository;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SimpleEntityServiceImpl implements SimpleEntityService {

    private final SimpleEntityRepository simpleEntityRepository;

    @Override
    public Page<SimpleEntity> findPageByInput(DataTablesInput input) {
        return simpleEntityRepository.findPageByInput(input);
    }

}
