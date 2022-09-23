package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.VariableRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.domain.model.mbg.VariableExample;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class VariableServiceImpl extends AbstractNodeService<Variable, VariableExample, Long> implements VariableService {

    private final VariableRepository repository;

    @Override
    protected VersionRepositoryInterface<Variable, VariableExample, Long> repository() {
        return repository;
    }

    @Override
    public List<Variable> findAllByTypeAndCode(String type, String code) {
        return repository.findAllByTypeAndCode(type, code);
    }

    @Override
    public List<Variable> findAllByTypeAndValueX(String type, int i, String value) {

        switch (i) {
            case 1:
                return repository.findAllByTypeAndValue1(type, value);

            case 2:
                return repository.findAllByTypeAndValue2(type, value);

            case 3:
                return repository.findAllByTypeAndValue3(type, value);

            case 4:
                return repository.findAllByTypeAndValue4(type, value);

            case 5:
                return repository.findAllByTypeAndValue5(type, value);

            case 6:
                return repository.findAllByTypeAndValue6(type, value);

            case 7:
                return repository.findAllByTypeAndValue7(type, value);

            case 8:
                return repository.findAllByTypeAndValue8(type, value);

            case 9:
                return repository.findAllByTypeAndValue9(type, value);

            case 10:
                return repository.findAllByTypeAndValue10(type, value);

            default:
                throw new IllegalArgumentException("i must 1 - 10.");
        }
    }

    @Override
    public List<Variable> findAllByType(String type) {
        return repository.findAllByType(type);
    }

    @Override
    public Page<Variable> findPageByInput(DataTablesInput input) {
        return repository.findPageByInput(input);
    }
}
