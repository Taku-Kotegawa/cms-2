package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.enums.VariableType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CodeListServiceImpl implements CodeListService {

    private final ApplicationContext applicationContext;

    @Override
    public void refresh(String codeListName) {

        try {
            String CodeListBeanName = VariableType.valueOf(codeListName).getCodeListBeanName();
            if (CodeListBeanName != null && !CodeListBeanName.isEmpty()) {
                ReloadableCodeList codeList = (ReloadableCodeList) applicationContext.getBean(CodeListBeanName);
                codeList.refresh();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }
}
