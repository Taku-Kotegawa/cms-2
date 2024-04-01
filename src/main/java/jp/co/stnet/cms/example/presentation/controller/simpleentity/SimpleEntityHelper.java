package jp.co.stnet.cms.example.presentation.controller.simpleentity;


import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.util.StateMap;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import jp.co.stnet.cms.example.presentation.dto.SimpleEntityBean;
import jp.co.stnet.cms.example.presentation.request.LineItemForm;
import jp.co.stnet.cms.example.presentation.request.SimpleEntityForm;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.codelist.CodeList;

import jakarta.inject.Named;
import jakarta.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityAuthority.validate;

@Component
public class SimpleEntityHelper {

    @Autowired
    @Named("CL_STATUS")
    CodeList statusCodeList;

    @Autowired
    ModelMapper modelMapper;

    // 許可されたOperation
    private static final Set<String> allowedOperation = Set.of(
            Constants.OPERATION.CREATE,
            Constants.OPERATION.UPDATE,
            Constants.OPERATION.VIEW
    );

    // 表示するボタン
    private static final Set<String> buttons = Set.of(
            Constants.BUTTON.GOTOLIST,
            Constants.BUTTON.GOTOUPDATE,
            Constants.BUTTON.VIEW,
            Constants.BUTTON.SAVE,
            Constants.BUTTON.VALID,
            Constants.BUTTON.INVALID,
            Constants.BUTTON.DELETE
    );

    /**
     * 画面に応じたボタンの状態を定義
     *
     * @param operation 操作
     * @param record    DBから取り出したデータ
     * @param form      画面から入力されたデータ
     * @return StateMap
     */
    StateMap getButtonStateMap(@NonNull String operation, SimpleEntity record, SimpleEntityForm form) {

        // 入力チェック
        validate(operation);

        if (record == null) {
            record = new SimpleEntity();
        }

        // StateMap初期化
        List<String> includeKeys = new ArrayList<>();
        includeKeys.addAll(buttons);
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
    StateMap getFiledStateMap(String operation, SimpleEntity record, SimpleEntityForm form) {

        // 常設の隠しフィールドは状態管理しない
        var excludeKeys = List.of("id", "version");
        StateMap fieldState = new StateMap(SimpleEntityForm.class, new ArrayList<>(), excludeKeys);

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

    public SimpleEntityBean copyFrom(SimpleEntity source) {

        var destination = new SimpleEntityBean();

        BeanUtils.copyProperties(source, destination);

        // テキストフィールド(複数の値)
        if (source.getText05() != null) {
            destination.setText05Label(String.join(",", source.getText05()));
        }

        // ラジオボタン(真偽値)ラベル
        if (source.getRadio01() != null) {
            destination.setRadio01Label(source.getRadio01() ? "はい" : "いいえ");
        }

        // チェックボックス(文字列)ラベル
        if (source.getCheckbox01() != null) {
            destination.setCheckbox01Label("はい".equals(source.getCheckbox01()) ? "☑" : "□" + "利用規約に合意する");
        }

        // チェックボックス(複数の値)ラベル
        if (source.getCheckbox02() != null) {
            String s = source.getCheckbox02().stream()
                    .map(str -> statusCodeList.asMap().get(str))
                    .collect(Collectors.joining(", "));
            destination.setCheckbox02Label(s);
        }

        // セレクト(単一の値)ラベル
        if (source.getSelect01() != null) {
            destination.setSelect01Label(statusCodeList.asMap().get(source.getSelect01()));
        }

        // セレクト(複数の値)
        if (source.getSelect02() != null) {
            String t = source.getSelect02().stream()
                    .map(str -> statusCodeList.asMap().get(str))
                    .collect(Collectors.joining(", "));
            destination.setSelect02Label(t);
        }

        // セレクト(単一の値, select2)
        if (source.getSelect03() != null) {
            destination.setSelect03Label(statusCodeList.asMap().get(source.getSelect03()));
        }

        // セレクト(複数の値, select2)
        String u = source.getSelect04().stream()
                .map(str -> statusCodeList.asMap().get(str))
                .collect(Collectors.joining(", "));
        destination.setSelect04Label(u);

        // コンボボックス(単一の値, Select2)
        if (source.getCombobox02() != null) {
            destination.setCombobox02Label(statusCodeList.asMap().get(source.getCombobox02()));
        }

        // コンボボックス(複数の値, Select2)
        if (source.getCombobox03() != null) {
            String v = source.getCombobox03().stream()
                    .map(str -> statusCodeList.asMap().get(str))
                    .collect(Collectors.joining(", "));
            destination.setCombobox03Label(v);
        }

        return destination;
    }


    /**
     * ListItemFormのリストがnull or Emptyの場合、１行の空白行のリストを返す。
     *
     * @param form SimpleEntityForm
     */
    void addLastOneEmptyLine(SimpleEntityForm form) {

        if (form.getLineItems() == null) {
            form.setLineItems(new ArrayList<LineItemForm>());
        }

        if (form.getLineItems().isEmpty()) {
            form.getLineItems().add(new LineItemForm());
        }

    }

}