package jp.co.stnet.cms.equipment.presentation.controller.organization;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.equipment.application.service.OrganizationService;
import jp.co.stnet.cms.equipment.domain.model.OrganizationBean;
import jp.co.stnet.cms.equipment.domain.model.mbg.Organization;
import jp.co.stnet.cms.equipment.presentation.dto.OrganizationListBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static jp.co.stnet.cms.equipment.presentation.controller.organization.OrganizationConstant.BASE_PATH;
import static jp.co.stnet.cms.equipment.presentation.controller.organization.OrganizationConstant.TEMPLATE_LIST;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class OrganizationListController {

    private final OrganizationService organizationService;
    private final OrganizationAuthority authority;

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        authority.hasAuthority(Constants.OPERATION.LIST, loggedInUser);
        return TEMPLATE_LIST;
    }

    /**
     * DataTables用のJSONの作成
     *
     * @param input DataTablesから要求
     * @return JSON
     */
    @ResponseBody
    @GetMapping(value = "/list/json")
    public DataTablesOutput<OrganizationListBean> listJson(@Validated DataTablesInput input,
                                                           @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.LIST, loggedInUser);

        List<OrganizationListBean> list = new ArrayList<>();
        Page<Organization> page = organizationService.findPageByInput(input);

        for (Organization vOrganization : page.getContent()) {
            list.add(OrganizationListBean.of(OrganizationBean.of(vOrganization)));
        }

        DataTablesOutput<OrganizationListBean> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(page.getTotalElements());

        return output;
    }

}
