package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.ConfirmHistory;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class ConfirmHistoryServiceImpl implements ConfirmHistoryService {
    @Override
    public ConfirmHistory findById(Long confirmId) {
        return null;
    }

    @Override
    public Page<ConfirmHistory> findPageByInput(DataTablesInput input) {
        return null;
    }

    @Override
    public List<ConfirmHistory> beginTakeInventory(LocalDateTime startDate) {
        return null;
    }

    @Override
    public ConfirmHistory checkExist(Long confirmId) {
        return null;
    }

    @Override
    public ConfirmHistory cancelCheck(Long confirmId) {
        return null;
    }
}
