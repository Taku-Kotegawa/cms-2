package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.AccountRole;
import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.AccountExample;
import jp.co.stnet.cms.base.domain.model.mbg.Role;
import jp.co.stnet.cms.base.domain.model.mbg.RoleExample;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.AccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Transactional
@Component
public class AccountRoleRepository implements VersionMapperInterface<AccountRole, AccountExample, String> {

    AccountMapper accountMapper;
    RoleMapper roleMapper;

    @Override
    public long countByExample(AccountExample example) {
        return accountMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(AccountExample example) {
        List<Account> accountList = accountMapper.selectByExample(example);
        List<String> usernameList = accountList.stream().map( x -> x.getUsername()).collect(Collectors.toList());
        for (String username: usernameList) {
            deleteRoleByUsername(username);
        }
        return accountMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String s) {
        deleteRoleByUsername(s);
        return accountMapper.deleteByPrimaryKey(s);
    }

    @Override
    public int insert(AccountRole row) {
        int i = accountMapper.insert(row);
        deleteInsertRole(row);
        return i;
    }

    @Override
    public int insertSelective(AccountRole row) {
        int i = accountMapper.insertSelective(row);
        if (row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return i;
    }

    @Override
    public List<AccountRole> selectByExampleWithRowbounds(AccountExample example, RowBounds rowBounds) {
        List<Account> accountList = accountMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<AccountRole> accountRoleList = new ArrayList<>();
        for (Account account : accountList) {
            accountRoleList.add(accountToAccountRole(account));
        }
        return accountRoleList;
    }

    @Override
    public List<AccountRole> selectByExample(AccountExample example) {
        List<Account> accountList = accountMapper.selectByExample(example);
        List<AccountRole> accountRoleList = new ArrayList<>();
        for (Account account : accountList) {
            accountRoleList.add(accountToAccountRole(account));
        }
        return accountRoleList;
    }

    @Override
    public AccountRole selectByPrimaryKey(String s) {
        Account account = accountMapper.selectByPrimaryKey(s);
        return accountToAccountRole(account);
    }

    @Override
    public int updateByExampleSelective(AccountRole row, AccountExample example) {
        if (row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return accountMapper.updateByExampleSelective(row, example);
    }

    @Override
    public int updateByExample(AccountRole row, AccountExample example) {
        deleteInsertRole(row);
        return accountMapper.updateByExample(row, example);
    }

    @Override
    public int updateByPrimaryKeySelective(AccountRole row) {
        if (row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return accountMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(AccountRole row) {
        deleteInsertRole(row);
        return accountMapper.updateByPrimaryKey(row);
    }

    @Override
    public int updateByPrimaryKeyAndVersionSelective(AccountRole row) {
        if (row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return accountMapper.updateByPrimaryKeyAndVersionSelective(row);
    }

    @Override
    public int updateByPrimaryKeyAndVersion(AccountRole row) {
        deleteInsertRole(row);
        return accountMapper.updateByPrimaryKeyAndVersion(row);
    }

    // Roleテーブルの洗い替え
    private void deleteInsertRole(AccountRole accountRole) {

        // usernameで削除
        deleteRoleByUsername(accountRole.getUsername());

        if (accountRole.getRoles() != null) {
            // Roleテーブルの挿入
            for (String roleName : accountRole.getRoles()) {
                Role role = new Role();
                role.setUsername(accountRole.getUsername());
                role.setRole(roleName);
                roleMapper.insert(role);
            }
        }
    }

    // ユーザ名でロール検索
    private List<Role> findRoleByUsername(String username) {
        RoleExample roleExample = new RoleExample();
        roleExample.or().andUsernameEqualTo(username);
        return roleMapper.selectByExample(roleExample);
    }

    private int deleteRoleByUsername(String username) {
        RoleExample roleExample = new RoleExample();
        roleExample.or().andUsernameEqualTo(username);
        return roleMapper.deleteByExample(roleExample);
    }

    // Account -> AccountRole変換
    private AccountRole accountToAccountRole(Account account) {
        if (account == null) { return null; }
        return AccountRole.from(account, findRoleByUsername(account.getUsername()));
    }

}
