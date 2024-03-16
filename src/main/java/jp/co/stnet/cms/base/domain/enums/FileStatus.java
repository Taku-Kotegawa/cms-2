package jp.co.stnet.cms.base.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

/**
 * ファイルステータス
 */
@RequiredArgsConstructor
@Getter
public enum FileStatus implements EnumCodeList.CodeListItem {

    TEMPORARY("0", "一時"),
    PERMANENT("1", "恒久");

    private final String value;
    private final String label;

    @Override
    public String getCodeValue() {
        return value;
    }

    @Override
    public String getCodeLabel() {
        return label;
    }

}
