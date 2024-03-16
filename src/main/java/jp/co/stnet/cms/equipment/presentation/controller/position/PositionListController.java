package jp.co.stnet.cms.equipment.presentation.controller.position;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.equipment.application.service.PositionService;
import jp.co.stnet.cms.equipment.domain.model.PositionBean;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import jp.co.stnet.cms.equipment.presentation.dto.PositionListBean;
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

import static jp.co.stnet.cms.equipment.presentation.controller.position.PositionConstant.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class PositionListController {

    private final PositionService positionService;
    private final PositionAuthority authority;

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        authority.hasAuthority(Constants.OPERATION.LIST, loggedInUser);
        model.addAttribute("pageTitle", PAGE_TITLE_LIST);
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
    public DataTablesOutput<PositionListBean> listJson(@Validated DataTablesInput input,
                                                       @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.LIST, loggedInUser);

        List<PositionListBean> list = new ArrayList<>();
        Page<Position> page = positionService.findPageByInput(input);

        for (Position vPosition : page.getContent()) {
            list.add(PositionListBean.of(PositionBean.of(vPosition)));
        }

        DataTablesOutput<PositionListBean> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(page.getTotalElements());

        return output;
    }

}
