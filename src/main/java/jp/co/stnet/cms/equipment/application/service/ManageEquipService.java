package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.ManageEquip;
import org.springframework.data.domain.Page;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

public interface ManageEquipService {

    /**
     * 主キーで検索する
     *
     * @param equipId 備品ID
     * @return 検索に合致したオブジェクト
     * @throws ResourceNotFoundException データが見つからない場合
     */
    ManageEquip findById(Long equipId);

    /**
     * 複数の条件で検索する(DataTables用)
     *
     * @param input 検索条件
     * @return 検索結果
     */
    Page<ManageEquip> findPageByInput(DataTablesInput input);

    /**
     * 備品を追加する
     *
     * @param entity 保存するオブジェクト
     * @return 保存後のオブジェクト
     */
    ManageEquip add(ManageEquip entity);

    /**
     * 備品情報を修正する
     *
     * @param entity 保存するオブジェクト
     * @return 保存後のオブジェクト
     */
    ManageEquip save(ManageEquip entity);

    /**
     * 備品情報を削除する
     *
     * @param equipId 備品ID
     */
    void delete(Long equipId);

}
