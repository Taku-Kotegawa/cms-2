package jp.co.stnet.cms.equipment.presentation.controller.position;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.util.StateMap;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import jp.co.stnet.cms.equipment.presentation.controller.position.PositionAuthority;
import jp.co.stnet.cms.equipment.presentation.controller.position.PositionConstant;
import jp.co.stnet.cms.equipment.presentation.dto.PositionForm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PositionHelper {

    private static final Set<String> buttons = PositionConstant.BUTTONS;
    private final PositionAuthority positionAuthority;

    /**
     * 画面に応じたボタンの状態を定義
     *
     * @param operation 操作
     * @param record    DBから取り出したデータ
     * @param form      画面から入力されたデータ
     * @return StateMap
     */
    StateMap getButtonStateMap(@NonNull String operation, Position record, PositionForm form) {

        // 入力チェック
        positionAuthority.validate(operation);

        if (record == null) {
            record = new Position();
        }

        // StateMap初期化
        List<String> includeKeys = new ArrayList<>(buttons);
        StateMap buttonState = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        buttonState.setViewTrue(Constants.BUTTON.GOTOLIST);

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            buttonState.setViewTrue(Constants.BUTTON.SAVE);
        }

        // 編集
        else if (Constants.OPERATION.UPDATE.equals(operation)) {

            // ステータス有効
            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.SAVE);
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.INVALID);
            }

            // ステータス無効
            else {
                buttonState.setViewTrue(Constants.BUTTON.VIEW);
                buttonState.setViewTrue(Constants.BUTTON.VALID);
                buttonState.setViewTrue(Constants.BUTTON.DELETE);
            }

        }

        // 参照
        else if (Constants.OPERATION.VIEW.equals(operation)) {

            // スタータス有効
            if (Status.VALID.getCodeValue().equals(record.getStatus())) {
                buttonState.setViewTrue(Constants.BUTTON.GOTOUPDATE);

            } else {
                // ステータス無効
                buttonState.setViewTrue(Constants.BUTTON.GOTOUPDATE);
            }
        }

        return buttonState;
    }

    /**
     * 画面に応じたフィールドの状態を定義
     *
     * @param operation 操作
     * @param record    DBから取り出したデータ
     * @param form      画面から入力されたデータ
     * @return StateMap
     */
    StateMap getFiledStateMap(String operation, Position record, PositionForm form) {

        // 常設の隠しフィールドは状態管理しない
        var excludeKeys = List.of("id", "version");
        StateMap fieldState = new StateMap(PositionForm.class, new ArrayList<>(), excludeKeys);

        fieldState.setReadOnlyTrue("positionId"); // 自動発番項目は常にReadOnly

        // 新規作成
        if (Constants.OPERATION.CREATE.equals(operation)) {
            fieldState.setInputTrueAll();
            fieldState.setInputFalse("status");
            fieldState.setHiddenTrue("status");
        }

        // 編集
        else if (Constants.OPERATION.UPDATE.equals(operation)) {
            fieldState.setInputTrueAll();

            // スタータスが無効
            if (Status.INVALID.getCodeValue().equals(record.getStatus())) {
                fieldState.setDisabledTrueAll();
            }
            fieldState.setInputFalse("status");
            fieldState.setHiddenTrue("status");
            fieldState.setViewTrue("status");
        }

        // 参照
        else if (Constants.OPERATION.VIEW.equals(operation)) {
            fieldState.setViewTrueAll();
        }

        return fieldState;
    }

}