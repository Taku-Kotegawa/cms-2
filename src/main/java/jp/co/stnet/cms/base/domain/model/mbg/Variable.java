package jp.co.stnet.cms.base.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.StatusInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Database Table Remarks:
 *   バリアブル
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table cms.variable
 */
@Data
@EqualsAndHashCode
@ToString
public class Variable implements Serializable, KeyInterface<Integer>, VersionInterface, StatusInterface {
    /**
     * Database Column Remarks:
     *   内部ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   バージョン
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.version
     *
     * @mbg.generated
     */
    private Long version;

    /**
     * Database Column Remarks:
     *   作成者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   作成日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.created_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * Database Column Remarks:
     *   最終更新者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.last_modified_by
     *
     * @mbg.generated
     */
    private String lastModifiedBy;

    /**
     * Database Column Remarks:
     *   最終更新日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.last_modified_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    /**
     * Database Column Remarks:
     *   ステータス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   タイプ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.type
     *
     * @mbg.generated
     */
    private String type;

    /**
     * Database Column Remarks:
     *   コード
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     * Database Column Remarks:
     *   値1
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value1
     *
     * @mbg.generated
     */
    private String value1;

    /**
     * Database Column Remarks:
     *   値2
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value2
     *
     * @mbg.generated
     */
    private String value2;

    /**
     * Database Column Remarks:
     *   値3
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value3
     *
     * @mbg.generated
     */
    private String value3;

    /**
     * Database Column Remarks:
     *   値4
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value4
     *
     * @mbg.generated
     */
    private String value4;

    /**
     * Database Column Remarks:
     *   値5
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value5
     *
     * @mbg.generated
     */
    private String value5;

    /**
     * Database Column Remarks:
     *   値6
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value6
     *
     * @mbg.generated
     */
    private String value6;

    /**
     * Database Column Remarks:
     *   値7
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value7
     *
     * @mbg.generated
     */
    private String value7;

    /**
     * Database Column Remarks:
     *   値8
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value8
     *
     * @mbg.generated
     */
    private String value8;

    /**
     * Database Column Remarks:
     *   値9
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value9
     *
     * @mbg.generated
     */
    private String value9;

    /**
     * Database Column Remarks:
     *   値10
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.value10
     *
     * @mbg.generated
     */
    private String value10;

    /**
     * Database Column Remarks:
     *   日付1
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.date1
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate date1;

    /**
     * Database Column Remarks:
     *   日付2
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.date2
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate date2;

    /**
     * Database Column Remarks:
     *   日付3
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.date3
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate date3;

    /**
     * Database Column Remarks:
     *   日付4
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.date4
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate date4;

    /**
     * Database Column Remarks:
     *   日付5
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.date5
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate date5;

    /**
     * Database Column Remarks:
     *   整数1
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.valint1
     *
     * @mbg.generated
     */
    private Integer valint1;

    /**
     * Database Column Remarks:
     *   整数2
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.valint2
     *
     * @mbg.generated
     */
    private Integer valint2;

    /**
     * Database Column Remarks:
     *   整数3
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.valint3
     *
     * @mbg.generated
     */
    private Integer valint3;

    /**
     * Database Column Remarks:
     *   整数4
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.valint4
     *
     * @mbg.generated
     */
    private Integer valint4;

    /**
     * Database Column Remarks:
     *   整数5
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.valint5
     *
     * @mbg.generated
     */
    private Integer valint5;

    /**
     * Database Column Remarks:
     *   ファイル1
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.file1uuid
     *
     * @mbg.generated
     */
    private String file1uuid;

    /**
     * Database Column Remarks:
     *   テキストエリア
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.textarea
     *
     * @mbg.generated
     */
    private String textarea;

    /**
     * Database Column Remarks:
     *   備考
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cms.variable.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cms.variable
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }
}