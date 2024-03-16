package jp.co.stnet.cms.common.constant;

/**
 * 共通定数
 */
public final class Constants {

    /**
     * リダイレクト
     */
    public static final String REDIRECT = "redirect:";

    /**
     * 操作
     */
    public static final class OPERATION {
        /** 新規作成画面を開く */
        public static final String CREATE = "create";
        /** 更新画面を開く */
        public static final String UPDATE = "update";
        /** 削除 */
        public static final String DELETE = "delete";
        /** 無効化 */
        public static final String INVALID = "invalid";
        /** 無効解除 */
        public static final String VALID = "valid";
        /** 本保存 */
        public static final String SAVE = "save";
        /** 下書き保存 */
        public static final String SAVE_DRAFT = "saveDraft";
        /** 下書き取り消し */
        public static final String CANCEL_DRAFT = "cancelDraft";
        /** 一覧画面の表示 */
        public static final String LIST = "list";
        /** 参照画面の表示 */
        public static final String VIEW = "view";
        /** ロック解除 */
        public static final String UNLOCK = "unlock";
        /** CSV等のダウンロード */
        public static final String DOWNLOAD = "download";
        /** CSV等のアップロード */
        public static final String UPLOAD = "upload";
        /** 複製して新規作成画面を開く */
        public static final String COPY = "copy";
        /** 一括削除 */
        public static final String BULK_DELETE = "bulkDelete";
        /** 一括無効化 */
        public static final String BULK_INVALID = "bulkInvalid";
        /** 一括無効解除 */
        public static final String BULK_VALID = "bulkValid";
        /** APIキーを設定 */
        public static final String SET_APIKEY = "setApiKey";
        /** APIキーの設定を解除 */
        public static final String UNSET_APIKEY = "unsetApiKey";
        /** APIキーを自動生成 */
        public static final String GENERATE_APIKEY = "generateApiKey";
        /** APIキーを削除 */
        public static final String DELETE_APIKEY = "deleteApiKey";
        /** APIキーを保存 */
        public static final String SAVE_APIKEY = "saveApiKey";
    }

    /**
     * ボタン
     */
    public static final class BUTTON {

        /*
         * ボタンのラベルは変更できる様に「OperationsUtil」で設定している
         */

        /** 一覧に戻る" */
        public static final String GOTOLIST = "gotoList";
        /** 編集 */
        public static final String GOTOUPDATE = "gotoUpdate";
        /** 新規作成 */
        public static final String CREATE = "create";
        /** 下書き保存 */
        public static final String SAVE_DRAFT = "saveDraft";
        /** 下書き取消 */
        public static final String CANCEL_DRAFT = "cancelDraft";
        /** 保存 */
        public static final String SAVE = "save";
        /** 無効化 */
        public static final String INVALID = "invalid";
        /** 無効解除 */
        public static final String VALID = "valid";
        /** 削除 */
        public static final String DELETE = "delete";
        /** 参照 */
        public static final String VIEW = "view";
        /** ロック解除 */
        public static final String UNLOCK = "unlock";
        /** ダウンロード */
        public static final String DOWNLOAD = "download";
        /** アップロード */
        public static final String UPLOAD = "upload";
        /** 確認 */
        public static final String CONFIRM = "confirm";
        /** 検証 */
        public static final String VALIDATE = "validate";
        /** ホームに戻る */
        public static final String GOHOME = "goHome";
        /** 複製 */
        public static final String COPY = "copy";
        /** 一括削除 */
        public static final String BULK_DELETE = "bulkDelete";
        /** 一括無効化 */
        public static final String BULK_INVALID = "bulkInvalid";
        /** 一括無効解除 */
        public static final String BULK_VALID = "bulkValid";
        /** APIをセット */
        public static final String SET_APIKEY = "setApiKey";
        /** APIキーを解除 */
        public static final String UNSET_APIKEY = "unsetApiKey";
        /** APIキーの生成 */
        public static final String GENERATE_APIKEY = "generateApiKey";
        /** APIキーの削除 */
        public static final String DELETE_APIKEY = "deleteApiKey";
        /** APIキーの保存 */
        public static final String SAVE_APIKEY = "saveApiKey";
        /** ユーザ切替 */
        public static final String SWITCH_USER = "switchUser";
        /** 明細追加 */
        public static final String ADD_ITEM = "addItem";
    }

    /**
     * CSV関連
     */
    public static final class CSV {
        public static final Integer MAX_LENGTH = 99999;
    }

    /**
     * Excel関連
     */
    public static final class EXCEL {
        public static final Integer MAX_LENGTH = 9999;
    }

    /**
     * メッセージ(プロパティファイルに記載できないもの)
     */
    public static final class MSG {
        public static final String VALIDATION_ERROR_STOP = "入力チェックでエラーを検出したため、処理を中断しました。";
        public static final String DB_ACCESS_ERROR_STOP = "データベース更新時にエラーが発生したため、処理を中断しました。";
    }

    /**
     * JOB_ID
     */
    public static final class JOBID {
        public static final String IMPORT_VARIABLE = "importVariable";
        public static final String IMPORT_ACCOUNT = "importAccount";
        public static final String IMPORT_DOCUMENT = "importDocument";

    }


}
