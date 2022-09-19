package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccount;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;
import jp.co.stnet.cms.base.domain.model.mbg.TRole;
import jp.co.stnet.cms.base.domain.model.mbg.TRoleExample;
import jp.co.stnet.cms.base.infrastructure.mapper.AccountQueryMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TAccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TRoleMapper;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Component
public class AccountRepository extends AbstractComplexVersionRepository<TAccount, TAccountExample, String, Account>
        implements VersionRepositoryInterface<Account, TAccountExample, String> {

    private final TAccountMapper tAccountMapper;
    private final TRoleMapper tRoleMapper;
    private final AccountQueryMapper accountQueryMapper;

    @Override
    VersionMapperInterface<TAccount, TAccountExample, String> mapper() {
        return tAccountMapper;
    }

    @Override
    TAccountExample example() {
        return new TAccountExample();
    }

    @Override
    protected void beforeDeleteAll() {
        deleteRoleAll();
    }

    @Override
    protected void beforeDelete(String id) {
        if (id != null) {
            deleteRoleByUsername(id);
        }
    }

    @Override
    protected void afterRegister(Account entity) {
        if (entity != null) {
            deleteInsertRole(entity);
        }
    }

    @Override
    protected void afterSave(Account entity) {
        if (entity != null) {
            deleteInsertRole(entity);
        }
    }

    @Override
    protected void afterFind(Account entity) {
        if (entity != null) {
            entity.setRoles(findRoleStringByUsername(entity.getUsername()));
        }
    }

    @Override
    protected Account cast(TAccount entity) {
        return Account.of(entity);
    }

    // -----------------------------------------------------------------------------------------------

    @Override
    public Optional<Account> findById(String id) {
        var account = accountQueryMapper.findById(id);
        return Optional.ofNullable(account);
    }

    public int updateApiKey(String username, String apiKey) {
        return tAccountMapper.updateByPrimaryKeySelective(
                TAccount.builder()
                        .username(username)
                        .apiKey(apiKey)
                        .build());
    }

    /**
     * アカウントを指定してロールテーブルを削除・挿入する。
     *
     * @param account アカウント
     */
    private void deleteInsertRole(Account account) {
        // usernameで削除
        deleteRoleByUsername(account.getUsername());
        if (account.getRoles() != null) {
            // Roleテーブルの挿入
            for (String roleName : account.getRoles()) {
                tRoleMapper.insert(TRole.builder()
                        .username(account.getUsername())
                        .role(roleName)
                        .build());
            }
        }
    }

    /**
     * ユーザ名を指定してロールを検索する
     *
     * @param username ユーザ名
     * @return ロールエンティティのリスト
     */
    private List<TRole> findRoleByUsername(String username) {
        var example = new TRoleExample();
        example.or().andUsernameEqualTo(username);
        return tRoleMapper.selectByExample(example);
    }

    /**
     * ユーザ名を指定してロールを検索する
     *
     * @param username ユーザ名
     * @return ロール名のリスト
     */
    private List<String> findRoleStringByUsername(String username) {
        return findRoleByUsername(username).stream().map(TRole::getRole).toList();
    }

    /**
     * ロールテーブルを全件削除
     *
     * @return 削除テーブル
     */
    private int deleteRoleAll() {
        return tRoleMapper.deleteByExample(new TRoleExample());
    }

    /**
     * ユーザ名を指定してロールテーブルから削除
     *
     * @param username ユーザ名
     * @return 削除件数
     */
    private int deleteRoleByUsername(String username) {
        var example = new TRoleExample();
        example.or().andUsernameEqualTo(username);
        return tRoleMapper.deleteByExample(example);
    }

    /**
     * ユーザ名を指定してロールテーブルから削除
     *
     * @param usernames ユーザ名のリスト
     * @return 削除件数
     */
    private int deleteRoleAllByUsername(List<String> usernames) {
        var example = new TRoleExample();
        example.or().andUsernameIn(usernames);
        return tRoleMapper.deleteByExample(example);
    }

    /**
     * DataTables用検索
     *
     * @param input DataTablesInput
     * @return 検索結果
     */
    public Page<Account> findPageByInput(DataTablesInput input) {
        input.setWhereClause(DataTablesUtil.getWhereClause(input, Account.class));
        var totalCount = mapper().countByExample(null);
        var pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());
        var entities = accountQueryMapper.findPageByInput(input, pageable);
        return new PageImpl<>(entities, pageable, totalCount);
    }

}
