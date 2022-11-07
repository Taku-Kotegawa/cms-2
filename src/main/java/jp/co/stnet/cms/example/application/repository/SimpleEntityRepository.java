package jp.co.stnet.cms.example.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractComplexVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesUtil;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import jp.co.stnet.cms.example.domain.model.mbg.*;
import jp.co.stnet.cms.example.infrastructure.mapper.SimpleEntityQueryMapper;
import jp.co.stnet.cms.example.infrastructure.mapper.mbg.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Component
public class SimpleEntityRepository extends AbstractComplexVersionRepository<TSimpleEntity, TSimpleEntityExample, Long, SimpleEntity>
        implements VersionRepositoryInterface<SimpleEntity, TSimpleEntityExample, Long> {

    private final TSimpleEntityMapper mapper;

    private final SimpleEntityCheckbox02Mapper simpleEntityCheckbox02Mapper;
    private final SimpleEntityCombobox03Mapper simpleEntityCombobox03Mapper;
    private final SimpleEntitySelect02Mapper simpleEntitySelect02Mapper;
    private final SimpleEntitySelect04Mapper simpleEntitySelect04Mapper;
    private final SimpleEntityText05Mapper simpleEntityText05Mapper;
    private final LineItemMapper lineItemMapper;

    private final SimpleEntityQueryMapper simpleEntityQueryMapper;

    @Override
    protected VersionMapperInterface<TSimpleEntity, TSimpleEntityExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected TSimpleEntityExample example() {
        return new TSimpleEntityExample();
    }

    @Override
    protected void beforeDeleteAll() {
        simpleEntityCheckbox02Mapper.deleteByExample(null);
        simpleEntityCombobox03Mapper.deleteByExample(null);
        simpleEntitySelect02Mapper.deleteByExample(null);
        simpleEntitySelect04Mapper.deleteByExample(null);
        simpleEntityText05Mapper.deleteByExample(null);
        lineItemMapper.deleteByExample(null);
    }

    @Override
    protected void beforeDelete(Long id) {
        var example1 = new SimpleEntityCheckbox02Example();
        example1.or().andSimpleEntityIdEqualTo(id);
        simpleEntityCheckbox02Mapper.deleteByExample(example1);

        var example2 = new SimpleEntityCombobox03Example();
        example2.or().andSimpleEntityIdEqualTo(id);
        simpleEntityCombobox03Mapper.deleteByExample(example2);

        var example3 = new SimpleEntitySelect02Example();
        example3.or().andSimpleEntityIdEqualTo(id);
        simpleEntitySelect02Mapper.deleteByExample(example3);

        var example4 = new SimpleEntitySelect04Example();
        example4.or().andSimpleEntityIdEqualTo(id);
        simpleEntitySelect04Mapper.deleteByExample(example4);

        var example5 = new SimpleEntityText05Example();
        example5.or().andSimpleEntityIdEqualTo(id);
        simpleEntityText05Mapper.deleteByExample(example5);

        var example6 = new LineItemExample();
        example6.or().andSimpleEntityIdEqualTo(id);
        lineItemMapper.deleteByExample(example6);

    }

    @Override
    protected void afterRegister(SimpleEntity entity) {

        if (entity.getCheckbox02() != null) {
            entity.getCheckbox02().forEach(x ->
                    simpleEntityCheckbox02Mapper.insert(
                            SimpleEntityCheckbox02.builder()
                                    .simpleEntityId(entity.getId())
                                    .checkbox02(x)
                                    .build()
                    ));
        }

        if (entity.getCombobox03() != null) {
            entity.getCombobox03().forEach(x ->
                    simpleEntityCombobox03Mapper.insert(
                            SimpleEntityCombobox03.builder()
                                    .simpleEntityId(entity.getId())
                                    .combobox03(x)
                                    .build()
                    ));
        }

        if (entity.getSelect02() != null) {
            entity.getSelect02().forEach(x ->
                    simpleEntitySelect02Mapper.insert(
                            SimpleEntitySelect02.builder()
                                    .simpleEntityId(entity.getId())
                                    .select02(x)
                                    .build()
                    ));
        }

        if (entity.getSelect04() != null) {
            entity.getSelect04().forEach(x ->
                    simpleEntitySelect04Mapper.insert(
                            SimpleEntitySelect04.builder()
                                    .simpleEntityId(entity.getId())
                                    .select04(x)
                                    .build()
                    ));
        }

        if (entity.getText05() != null) {
            entity.getText05().forEach(x ->
                    simpleEntityText05Mapper.insert(
                            SimpleEntityText05.builder()
                                    .simpleEntityId(entity.getId())
                                    .text05(x)
                                    .build()
                    ));
        }

        if (entity.getLineItems() != null) {
            long i = 0L;
            for (LineItem lineItem : entity.getLineItems()) {
                lineItem.setSimpleEntityId(entity.getId());
                lineItem.setItemNo(i);
                lineItemMapper.insert(lineItem);
                i++;
            }
        }

    }

    @Override
    protected void afterSave(SimpleEntity entity) {
        beforeDelete(entity.getId());
        afterRegister(entity);
    }

    @Override
    protected void afterFind(SimpleEntity entity) {
        // 使わない
    }

    @Override
    protected SimpleEntity cast(TSimpleEntity entity) {
        var target = new SimpleEntity();
        BeanUtils.copyProperties(entity, target);
        return target;
    }

    private final Map<String, String> fieldMap = jp.co.stnet.cms.common.util.BeanUtils.getFields(SimpleEntity.class, null);

    /**
     * DataTables用検索
     *
     * @param input DataTablesInput
     * @return 検索結果
     */
    public Page<SimpleEntity> findPageByInput(DataTablesInput input) {
        input.setWhereClause(DataTablesUtil.getWhereClause(input, fieldMap));
        var pageable = input.getPageable();
        var entities = simpleEntityQueryMapper.findByInput(input);
        var totalCount = simpleEntityQueryMapper.countByInput(input);
        return new PageImpl<>(entities, pageable, totalCount);
    }

    @Override
    public Optional<SimpleEntity> findById(Long id) {
        return Optional.ofNullable(simpleEntityQueryMapper.findByPrimaryKey(id));
    }
}
