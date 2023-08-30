package jp.co.stnet.cms.base.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

/**
 * パーミッション Enum
 */
@RequiredArgsConstructor
@Getter
public enum Permission implements EnumCodeList.CodeListItem {

    VIEW_ALL_NODE("全コンテンツの参照", "NODE"),
    VIEW_OWN_NODE("自分のコンテンツの参照", "NODE"),
    ADMIN_USER("ユーザの管理", "ADMIN"),
    ADMIN_ROLE("ロールの管理", "ADMIN"),
    ADMIN_PERMISSION("パーミッションの管理", "ADMIN"),
    ADMIN_MENU("管理者メニューを開く", "ADMIN"),
    ADMIN_VARIABLE("バリアルブルの管理", "ADMIN"),
    ADMIN_ACCESS_COUNTER("アクセスカウンタの管理", "ADMIN"),
    DOC_MAN_CREATE("新規登録", "DOC"),
    DOC_MAN_UPDATE("編集", "DOC"),
    DOC_MAN_SAVE_DRAFT("下書き保存/下書取消", "DOC"),
    DOC_MAN_SAVE("保存", "DOC"),
    DOC_MAN_INVALID("無効化/無効解除", "DOC"),
    DOC_MAN_DELETE("削除", "DOC"),
    DOC_MAN_UPLOAD("アップロード", "DOC"),
    DOC_MAN_LIST("管理一覧を表示", "DOC"),
    DOC_LIST("検索一覧を表示", "DOC"),
    DOC_SEARCH("全文検索一覧を表示", "DOC"),
    DOC_VIEW_ALL("参照(社員)", "DOC"),
    DOC_VIEW_DISPATCHED_LABOR("参照(派遣)", "DOC"),
    DOC_VIEW_OUTSOURCING("参照(外部委託)", "DOC"),
    SENTITY_CREATE("新規登録", "SENTITY"),
    SENTITY_UPDATE("編集", "SENTITY"),
    SENTITY_SAVE_DRAFT("下書き保存/下書取消", "SENTITY"),
    SENTITY_SAVE("保存", "SENTITY"),
    SENTITY_INVALID("無効化/無効解除", "SENTITY"),
    SENTITY_DELETE("削除", "SENTITY"),
    SENTITY_UPLOAD("アップロード", "SENTITY"),
    SENTITY_LIST("管理一覧を表示", "SENTITY"),
    JOBLOG_ADMIN("全てのジョブログを閲覧", "JOBLOG"),
    JOBLOG_VIEW_SELF("自分のジョブログを閲覧", "JOBLOG"),
    EMPLOYEE_CREATE("新規登録", "EMPLOYEE"),
    EMPLOYEE_UPDATE("編集", "EMPLOYEE"),
    EMPLOYEE_SAVE("保存", "EMPLOYEE"),
    EMPLOYEE_INVALID("無効化/無効解除", "EMPLOYEE"),
    EMPLOYEE_DELETE("削除", "EMPLOYEE"),
    EMPLOYEE_UPLOAD("アップロード", "EMPLOYEE"),
    EMPLOYEE_LIST("一覧を表示", "EMPLOYEE"),

    ORGANIZATION_CREATE("新規登録", "ORGANIZATION"),
    ORGANIZATION_UPDATE("編集", "ORGANIZATION"),
    ORGANIZATION_SAVE("保存", "ORGANIZATION"),
    ORGANIZATION_INVALID("無効化/無効解除", "ORGANIZATION"),
    ORGANIZATION_DELETE("削除", "ORGANIZATION"),
    ORGANIZATION_UPLOAD("アップロード", "ORGANIZATION"),
    ORGANIZATION_LIST("一覧を表示", "ORGANIZATION"),

    POSITION_CREATE("新規登録", "POSITION"),
    POSITION_UPDATE("編集", "POSITION"),
    POSITION_SAVE("保存", "POSITION"),
    POSITION_INVALID("無効化/無効解除", "POSITION"),
    POSITION_DELETE("削除", "POSITION"),
    POSITION_UPLOAD("アップロード", "POSITION"),
    POSITION_LIST("一覧を表示", "POSITION"),
    
    ;

    /**
     * ラベル(日本語)
     */
    private final String label;

    /**
     * カテゴリ
     */
    private final String category;

    @Override
    public String getCodeLabel() {
        return label;
    }

    @Override
    public String getCodeValue() {
        return name();
    }

}
