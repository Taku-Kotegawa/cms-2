package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.ConfirmHistory;
import org.springframework.data.domain.Page;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface ConfirmHistoryService {

    /**
     * 主キーで検索する
     *
     * @param confirmId
     * @return 検索に合致したオブジェクト
     * @throws ResourceNotFoundException データが見つからない場合
     */
    ConfirmHistory findById(Long confirmId);

    /**
     * 複数の条件で検索する(DataTables用)
     *
     * @param input 検索条件
     * @return 検索結果
     */
    Page<ConfirmHistory> findPageByInput(DataTablesInput input);

    /**
     * 指定した年度の棚卸しを開始する
     *
     * @param startDate 棚卸し開始日
     * @return 作成された棚卸しのリスト
     * @throws IllegalArgumentException 棚卸し開始日が不正
     */
    List<ConfirmHistory> beginTakeInventory(LocalDateTime startDate);

    /**
     * 備品の存在を確認した
     *
     * @param confirmId 取り消しする棚卸履歴ID
     * @return 変更後のオブジェクト
     * @throws ResourceNotFoundException データが見つからない場合
     */
    ConfirmHistory checkExist(Long confirmId);

    /**
     * 確認の取り消し
     *
     * @param confirmId 取り消しする棚卸履歴ID
     * @return 変更後のオブジェクト
     * @throws ResourceNotFoundException データが見つからない場合
     */
    ConfirmHistory cancelCheck(Long confirmId);

}
