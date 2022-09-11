package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccount;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;
import jp.co.stnet.cms.base.domain.model.mbg.TRole;
import jp.co.stnet.cms.base.domain.model.mbg.TRoleExample;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TAccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TRoleMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Component
public class AccountTestRepository {

    private final TAccountMapper accountMapper;
    private final TRoleMapper roleMapper;

    public long countByExample(TAccountExample example) {
        return accountMapper.countByExample(example);
    }

    public int deleteByExample(TAccountExample example) {
        List<TAccount> accountList = accountMapper.selectByExample(example);
        List<String> usernameList = accountList.stream().map(x -> x.getUsername()).collect(Collectors.toList());
        for (String username : usernameList) {
            deleteRoleByUsername(username);
        }
        return accountMapper.deleteByExample(example);
    }

    public int deleteByPrimaryKey(String s) {
        deleteRoleByUsername(s);
        return accountMapper.deleteByPrimaryKey(s);
    }

    public int insert(Account row) {
        int i = accountMapper.insert(row);
        deleteInsertRole(row);
        return i;
    }

    public int insertSelective(Account row) {
        int i = accountMapper.insertSelective(row);
        if (row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return i;
    }

    public List<Account> selectByExampleWithRowbounds(TAccountExample example, RowBounds rowBounds) {
        List<jp.co.stnet.cms.base.domain.model.mbg.TAccount> accountList = accountMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<Account> accountAndRolesRoleList = new ArrayList<>();
        for (jp.co.stnet.cms.base.domain.model.mbg.TAccount account : accountList) {
            accountAndRolesRoleList.add(taccountToAccount(account));
        }
        return accountAndRolesRoleList;
    }


    public List<Account> selectByExample(TAccountExample example) {
        List<jp.co.stnet.cms.base.domain.model.mbg.TAccount> accountList = accountMapper.selectByExample(example);
        List<Account> accountAndRolesRoleList = new ArrayList<>();
        for (jp.co.stnet.cms.base.domain.model.mbg.TAccount account : accountList) {
            accountAndRolesRoleList.add(taccountToAccount(account));
        }
        return accountAndRolesRoleList;
    }

    public Account selectByPrimaryKey(String s) {
        jp.co.stnet.cms.base.domain.model.mbg.TAccount account = accountMapper.selectByPrimaryKey(s);
        return taccountToAccount(account);
    }


    public int updateByExampleSelective(Account row, TAccountExample example) {
        if (row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return accountMapper.updateByExampleSelective(row, example);
    }


    public int updateByExample(Account row, TAccountExample example) {
        deleteInsertRole(row);
        return accountMapper.updateByExample(row, example);
    }


    public int updateByPrimaryKeySelective(Account row) {
        var count = accountMapper.updateByPrimaryKeySelective(row);
        if (0 < count && row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return count;
    }


    public int updateByPrimaryKey(Account row) {
        var count = accountMapper.updateByPrimaryKey(row);
        if (0 < count && row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return count;
    }


    public int updateByPrimaryKeyAndVersionSelective(Account row) {
        var count = accountMapper.updateByPrimaryKeyAndVersionSelective(row);
        if (0 < count && row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return count;
    }


    public int updateByPrimaryKeyAndVersion(Account row) {
        var count = accountMapper.updateByPrimaryKeyAndVersion(row);
        if (0 < count && row.getRoles() != null) {
            deleteInsertRole(row);
        }
        return count;
    }

    // Roleテーブルの洗い替え
    private void deleteInsertRole(Account account) {
        // usernameで削除
        deleteRoleByUsername(account.getUsername());

        if (account.getRoles() != null) {
            // Roleテーブルの挿入
            for (String roleName : account.getRoles()) {
                var role = new TRole();
                role.setUsername(account.getUsername());
                role.setRole(roleName);
                roleMapper.insert(role);
            }
        }
    }

    // ユーザ名でロール検索
    private List<TRole> findRoleByUsername(String username) {
        var example = new TRoleExample();
        example.or().andUsernameEqualTo(username);
        return roleMapper.selectByExample(example);
    }

    private int deleteRoleByUsername(String username) {
        var example = new TRoleExample();
        example.or().andUsernameEqualTo(username);
        return roleMapper.deleteByExample(example);
    }

    // Account -> AccountRole変換
    private Account taccountToAccount(TAccount taccount) {
        if (taccount == null) {
            return null;
        }
        Account newAccount = Account.of(taccount);
        newAccount.setRoles(findRoleByUsername(taccount.getUsername()).stream().map(TRole::getRole).toList());
        return newAccount;
    }

}
