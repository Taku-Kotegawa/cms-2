package jp.co.stnet.cms.example.presentation.controller;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.datatables.*;
import jp.co.stnet.cms.common.util.BeanUtils;
import jp.co.stnet.cms.example.application.service.SimpleEntityService;
import jp.co.stnet.cms.example.domain.model.mbg.TSimpleEntity;
import jp.co.stnet.cms.example.presentation.request.SimpleEntityForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import static jp.co.stnet.cms.example.presentation.controller.SimpleEntityConstant.BASE_PATH;
import static jp.co.stnet.cms.example.presentation.controller.SimpleEntityConstant.TEMPLATE_LIST;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class SimpleEntityListController {

    private final SimpleEntityService simpleEntityService;

    /**
     * 一覧画面の表示
     */
    @GetMapping("list")
    public String search(Model model,
                         @Validated SimpleEntityForm form,
                         BindingResult bindingResult,
                         @PageableDefault(size = 10) Pageable pageable,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        var page = simpleEntityService.findPageByInput(createDataTablesInput(form, pageable));

        model.addAttribute("page", page);
        model.addAttribute("form", form);
        model.addAttribute("sortHelper", new SortHelper(pageable.getSort()));

        return TEMPLATE_LIST;
    }

    /**
     * SimpleEntityForm と Pageable から DataTablesInputs を作る
     *
     * @param form     SimpleEntityForm
     * @param pageable Pageable
     * @return DataTablesInput
     */
    private DataTablesInput createDataTablesInput(SimpleEntityForm form, Pageable pageable) {

        var fields = BeanUtils.getFieldList(TSimpleEntity.class);

        var input = DataTablesInput.builder()
                .draw(1)
                .start(Long.valueOf(pageable.getOffset()).intValue())
                .length(pageable.getPageSize())
                .search(Search.builder()
                        .value(form.getQ())
                        .regex(false)
                        .build())
                .order(new ArrayList<>())
                .columns(
                        fields.stream().map(x ->
                                Column.builder()
                                        .data(x)
                                        .name(null)
                                        .searchable(true)
                                        .orderable(true)
                                        .search(Search.builder().regex(false).build())
                                        .build()).toList()
                )
                .build();

        for (var sort : pageable.getSort()) {
            var column = input.getColumn(sort.getProperty());
            if (column != null) {
                input.getOrder().add(Order.builder()
                        .column(input.getColumnNumber(column.getData()))
                        .dir(sort.getDirection().toString().toLowerCase())
                        .build());
            }
        }

        return input;
    }

}
