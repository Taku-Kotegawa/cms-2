package jp.co.stnet.cms.base.presentation.controller.admin.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountCsvDlBean {

    /**
     * ユーザID
     */
    @JsonProperty(index = 1)
    private String username;

    /**
     * 名
     */
    @JsonProperty(index = 2)
    private String firstName;

    /**
     * 姓
     */
    @JsonProperty(index = 3)
    private String lastName;

    /**
     * 所属
     */
    @JsonProperty(index = 4)
    private String department;

    /**
     * メールアドレス
     */
    @JsonProperty(index = 5)
    private String email;

    /**
     * URL
     */
    @JsonProperty(index = 6)
    private String url;

    /**
     * プロフィール
     */
    @JsonProperty(index = 7)
    private String profile;

    /**
     * ロール
     */
    @JsonProperty(index = 8)
    private String roles;

    /**
     * ステータス
     */
    @JsonProperty(index = 9)
    private String status;

    /**
     * ステータス(ラベル)
     */
    @JsonProperty(index = 10)
    private String statusLabel;

    /**
     * 画像UUID
     */
    @JsonProperty(index = 11)
    private String imageUuid;

    /**
     * API KEY
     */
    @JsonProperty(index = 12)
    private String apiKey;

    /**
     * ログイン許可IPアドレス
     */
    @JsonProperty(index = 13)
    private String allowedIp;

}
