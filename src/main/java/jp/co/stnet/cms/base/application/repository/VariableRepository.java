package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.domain.model.mbg.VariableExample;
import jp.co.stnet.cms.base.infrastructure.mapper.VariableQueryMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.VariableMapper;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesUtil;
import jp.co.stnet.cms.common.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Transactional
@Component
public class VariableRepository extends AbstractVersionRepository<Variable, VariableExample, Long>
        implements VersionRepositoryInterface<Variable, VariableExample, Long> {

    private final VariableMapper mapper;
    private final VariableQueryMapper variableQueryMapper;
    private final Map<String, String> fieldMap = BeanUtils.getFields(Variable.class, null);

    @Override
    VersionMapperInterface<Variable, VariableExample, Long> mapper() {
        return mapper;
    }

    @Override
    VariableExample example() {
        return new VariableExample();
    }

    public List<Variable> findAllByTypeAndCode(String type, String code) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andCodeEqualTo(code);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue1(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue1EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue2(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue2EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue3(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue3EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue4(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue4EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue5(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue5EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue6(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue6EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue7(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue7EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue8(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue8EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue9(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue9EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByTypeAndValue10(String type, String value) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type)
                .andValue10EqualTo(value);
        return mapper().selectByExample(example);
    }

    public List<Variable> findAllByType(String type) {
        var example = new VariableExample();
        example.or()
                .andTypeEqualTo(type);
        return mapper().selectByExample(example);
    }

    /**
     * DataTables用検索
     *
     * @param input DataTablesInput
     * @return 検索結果
     */
    public Page<Variable> findPageByInput(DataTablesInput input) {
        input.setWhereClause(DataTablesUtil.getWhereClause(input, fieldMap));
        var pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());
        var entities = variableQueryMapper.findPageByInput(input, pageable);
        var totalCount = variableQueryMapper.countByInput(input);
        return new PageImpl<>(entities, pageable, totalCount);
    }

}