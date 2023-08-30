package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.application.service.AbstractNodeService;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.application.repository.OrganizationRepository;
import jp.co.stnet.cms.equipment.domain.model.mbg.Organization;
import jp.co.stnet.cms.equipment.domain.model.mbg.OrganizationExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationServiceImpl extends AbstractNodeService<Organization, OrganizationExample, Long> implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    protected VersionRepositoryInterface<Organization, OrganizationExample, Long> repository() {
        return repository;
    }

    @Override
    public Page<Organization> findPageByInput(DataTablesInput input) {
        return repository.findPageByInput(input);
    }
}