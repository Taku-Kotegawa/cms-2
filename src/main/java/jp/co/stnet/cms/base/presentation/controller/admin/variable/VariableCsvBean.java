package jp.co.stnet.cms.base.presentation.controller.admin.variable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 変数管理のCSVファイルのBean
 */
@Data
public class VariableCsvBean implements Serializable {

    /**
     * 内部ID
     */
    @JsonProperty(index = 1)
    private Long id;

    /**
     * バージョン
     */
    @JsonProperty(index = 2)
    private Long version;

    /**
     * ステータス
     */
    @JsonProperty(index = 3)
    private String status;

    /**
     * ステータス
     */
    @JsonProperty(index = 4)
    private String statusLabel;

    /**
     * タイプ
     */
    @JsonProperty(index = 5)
    @NotNull
    private String type;

    /**
     * コード
     */
    @JsonProperty(index = 6)
    @NotNull
    private String code;

    /**
     * 値1
     */
    @JsonProperty(index = 7)
    private String value1;

    /**
     * 値2
     */
    @JsonProperty(index = 8)
    private String value2;

    /**
     * 値3
     */
    @JsonProperty(index = 9)
    private String value3;

    /**
     * 値4
     */
    @JsonProperty(index = 10)
    private String value4;

    /**
     * 値5
     */
    @JsonProperty(index = 11)
    private String value5;

    /**
     * 値6
     */
    @JsonProperty(index = 12)
    private String value6;

    /**
     * 値7
     */
    @JsonProperty(index = 13)
    private String value7;

    /**
     * 値8
     */
    @JsonProperty(index = 14)
    private String value8;

    /**
     * 値9
     */
    @JsonProperty(index = 15)
    private String value9;

    /**
     * 値10
     */
    @JsonProperty(index = 16)
    private String value10;

    /**
     * 日付1
     */
    @JsonProperty(index = 17)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date1;

    /**
     * 日付2
     */
    @JsonProperty(index = 18)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date2;

    /**
     * 日付3
     */
    @JsonProperty(index = 19)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date3;

    /**
     * 日付4
     */
    @JsonProperty(index = 20)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date4;

    /**
     * 日付5
     */
    @JsonProperty(index = 21)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date5;

    /**
     * 整数1
     */
    @JsonProperty(index = 22)
    private Integer valint1;

    /**
     * 整数2
     */
    @JsonProperty(index = 23)
    private Integer valint2;

    /**
     * 整数3
     */
    @JsonProperty(index = 24)
    private Integer valint3;

    /**
     * 整数4
     */
    @JsonProperty(index = 25)
    private Integer valint4;

    /**
     * 整数5
     */
    @JsonProperty(index = 26)
    private Integer valint5;

    /**
     * テキストエリア
     */
    @JsonProperty(index = 27)
    private String textarea;

    /**
     * ファイル1
     */
    @JsonProperty(index = 28)
    private String file1Uuid;

    /**
     * 備考
     */
    @JsonProperty(index = 29)
    private String remark;

}