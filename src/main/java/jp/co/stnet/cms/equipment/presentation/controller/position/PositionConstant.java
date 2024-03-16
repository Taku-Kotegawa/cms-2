package jp.co.stnet.cms.equipment.presentation.controller.position;

import jp.co.stnet.cms.common.constant.Constants;

import java.util.Set;

public class PositionConstant {

    // Viewファイル(.html)のパス
    static final String BASE_PATH = "equipment/position";
    static final String TEMPLATE_LIST = BASE_PATH + "/list";
    static final String TEMPLATE_FORM = BASE_PATH + "/form";
    static final String TEMPLATE_UPLOAD_FORM = BASE_PATH + "/uploadform";
    static final String TEMPLATE_UPLOAD_COMPLETE = "upload/complete";

    // ページタイトル
    static final String PAGE_TITLE = "役職";
    static final String PAGE_TITLE_LIST = PAGE_TITLE + " 一覧";
    static final String PAGE_TITLE_CREATE = PAGE_TITLE + " 新規作成";
    static final String PAGE_TITLE_UPDATE = PAGE_TITLE + " 編集";
    static final String PAGE_TITLE_DELETE = PAGE_TITLE + " 削除";
    static final String PAGE_TITLE_VIEW = PAGE_TITLE;

    // CSV/TSVのファイル名(拡張子除く)
    static final String DOWNLOAD_FILENAME = "equipment-position";

    // アップロード用のインポートジョブID
    static final String UPLOAD_JOB_ID = "uploadEquipmentPosition";

    // 許可された操作
    static final Set<String> ALLOWED_OPERATION = Set.of(
            Constants.OPERATION.CREATE,
            Constants.OPERATION.UPDATE,
            Constants.OPERATION.DELETE,
            Constants.OPERATION.SAVE,
            // Constants.OPERATION.SAVE_DRAFT,
            // Constants.OPERATION.CANCEL_DRAFT,
            Constants.OPERATION.INVALID,
            Constants.OPERATION.VALID,
            Constants.OPERATION.UPLOAD,
            Constants.OPERATION.VIEW,
            Constants.OPERATION.LIST,
            Constants.OPERATION.DOWNLOAD
    );

    // 新規作成・編集・参照画面に表示するボタン
    static final Set<String> BUTTONS = Set.of(
            Constants.BUTTON.GOTOLIST,
            Constants.BUTTON.GOTOUPDATE,
            Constants.BUTTON.VIEW,
            Constants.BUTTON.SAVE,
            Constants.BUTTON.VALID,
            Constants.BUTTON.INVALID,
            Constants.BUTTON.DELETE
    );

}
