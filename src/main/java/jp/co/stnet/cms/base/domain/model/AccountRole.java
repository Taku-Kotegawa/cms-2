package jp.co.stnet.cms.base.domain.model;


import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountRole extends Account implements Serializable {

    /**
     * ロール
     */
    List<String> roles;

    public static AccountRole from(Account account, List<Role> roleList) {
        if (account == null) { return null; }
        AccountRole accountRole = new AccountRole();
        BeanUtils.copyProperties(account, accountRole);
        accountRole.setRoles(roleToString(roleList));
        return accountRole;
    }

    private static List<String> roleToString(List<Role> roleList) {
        List<String> roleNameList = new ArrayList<>();
        for (Role role : roleList) {
            roleNameList.add(role.getRole());
        }
        return roleNameList;
    }

}
