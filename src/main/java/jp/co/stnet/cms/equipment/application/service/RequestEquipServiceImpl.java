package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.ApprovalFlow;
import jp.co.stnet.cms.equipment.domain.model.mbg.RequestEquip;
import org.springframework.data.domain.Page;

public class RequestEquipServiceImpl implements RequestEquipService {
    @Override
    public RequestEquip findById(Long requestId) {
        return null;
    }

    @Override
    public Page<RequestEquip> findPageByInput(DataTablesInput input) {
        return null;
    }

    @Override
    public RequestEquip draft(ApprovalFlow entity) {
        return null;
    }

    @Override
    public RequestEquip apply(Long requestId) {
        return null;
    }

    @Override
    public RequestEquip setApprover(Long requestId, int step, Long empId) {
        return null;
    }

    @Override
    public RequestEquip approval(Long requestId) {
        return null;
    }

    @Override
    public RequestEquip rejectPrev(Long requestId) {
        return null;
    }

    @Override
    public RequestEquip rejectStart(Long requestId) {
        return null;
    }

    @Override
    public RequestEquip stockUp(Long requestId) {
        return null;
    }

    @Override
    public void printPdf(Long requestId) {

    }
}
