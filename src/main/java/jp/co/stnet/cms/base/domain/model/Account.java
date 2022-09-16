package jp.co.stnet.cms.base.domain.model;

import jp.co.stnet.cms.base.domain.model.mbg.TAccount;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account extends TAccount {

    /**
     * ロール
     */
    List<String> roles = new ArrayList<>();

    /**
     * TAccount -> Account 変換(新規インスタンス作成)
     *
     * @param tAccount TAccountエンティティ
     * @return 新規Accountエンティティ
     */
    public static Account of(TAccount tAccount) {
        if (tAccount == null) {return null;}
        var newEntity = new Account();
        BeanUtils.copyProperties(tAccount, newEntity);
        return newEntity;
    }

}
