package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.ManageEquip;
import org.springframework.data.domain.Page;

public class ManageEquipServiceImpl implements ManageEquipService {
    @Override
    public ManageEquip findById(Long equipId) {
        return null;
    }

    @Override
    public Page<ManageEquip> findPageByInput(DataTablesInput input) {
        return null;
    }

    @Override
    public ManageEquip add(ManageEquip entity) {
        return null;
    }

    @Override
    public ManageEquip save(ManageEquip entity) {
        return null;
    }

    @Override
    public void delete(Long equipId) {

    }
}
