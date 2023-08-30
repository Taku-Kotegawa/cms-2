package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.equipment.domain.model.mbg.ApprovalFlow;
import jp.co.stnet.cms.equipment.domain.model.mbg.RequestEquip;
import org.springframework.data.domain.Page;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

public interface RequestEquipService {

    /**
     * 主キーで検索する
     *
     * @param requestId
     * @return 検索に合致したオブジェクト
     * @throws ResourceNotFoundException データが見つからない場合
     */
    RequestEquip findById(Long requestId);

    /**
     * 複数の条件で検索する(DataTables用)
     *
     * @param input 検索条件
     * @return 検索結果
     */
    Page<RequestEquip> findPageByInput(DataTablesInput input);

    /**
     * 下書き保存
     *
     * @param entity 登録するオブジェクト
     * @return 登録したオブジェクト
     */
    RequestEquip draft(ApprovalFlow entity);

    /**
     * 申請する(備品購入を依頼する)
     *
     * @param requestId 購入依頼ID
     * @return 申請後のオブジェクト
     */
    RequestEquip apply(Long requestId);

    /**
     * 購入依頼を承認する(１次、２次承認者共用)
     *
     * @param requestId 購入依頼ID
     * @param step      第１承認者、第２承認者の指定(１ or 2)
     * @param empId     承認者の従業員ID
     * @return 承認後のオブジェクト
     * @throws IllegalStateBusinessException 承認失敗(承認できないステータスの場合)
     */
    RequestEquip setApprover(Long requestId, int step, Long empId);

    /**
     * 購入依頼を承認する(１次、２次承認者共用)
     *
     * @param requestId 購入依頼ID
     * @return 承認後のオブジェクト
     * @throws IllegalStateBusinessException 承認失敗(承認できないステータスの場合)
     */
    RequestEquip approval(Long requestId);

    /**
     * １つ前の申請者に差し戻す(１次、２次承認者共用)
     *
     * @param requestId 購入依頼ID
     * @return 差し戻し後のオブジェクト
     * @throws IllegalStateBusinessException 承認失敗(承認できないステータスの場合)
     */
    RequestEquip rejectPrev(Long requestId);

    /**
     * 最初の申請者に差し戻す(１次、２次承認者共用)
     *
     * @param requestId 購入依頼ID
     * @return 差し戻し後のオブジェクト
     * @throws IllegalStateBusinessException 承認失敗(承認できないステータスの場合)
     */
    RequestEquip rejectStart(Long requestId);

    /**
     * 入庫する(備品管理台帳に追記する)
     *
     * @param requestId 購入依頼ID
     * @return 入庫後のオブジェクト
     */
    RequestEquip stockUp(Long requestId);

    /**
     * TODO 購入依頼表のPDF作成
     *
     * @param requestId 購入依頼ID
     */
    void printPdf(Long requestId);

}
