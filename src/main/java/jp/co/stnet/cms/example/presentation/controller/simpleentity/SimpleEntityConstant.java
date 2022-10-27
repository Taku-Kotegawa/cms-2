package jp.co.stnet.cms.example.presentation.controller.simpleentity;

import jp.co.stnet.cms.common.constant.Constants;

public class SimpleEntityConstant {

    static final String BASE_PATH = "example/simpleentity";
    static final String TEMPLATE_LIST = BASE_PATH + "/list";
    static final String TEMPLATE_FORM = BASE_PATH + "/form";
    static final String TEMPLATE_UPLOAD_FORM = BASE_PATH + "/uploadform";
    static final String TEMPLATE_UPLOAD_COMPLETE = "upload/complete";

    // CSV/TSVのファイル名(拡張子除く)
    static final String DOWNLOAD_FILENAME = "simple-entity";

    // アップロード用のインポートジョブID
    static final String UPLOAD_JOB_ID = "uploadSimpleEntity";

}
