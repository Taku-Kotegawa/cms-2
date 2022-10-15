package jp.co.stnet.cms.base.presentation.view;

import jp.co.stnet.cms.common.util.JacksonCsvUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.web.download.AbstractFileDownloadView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class TsvDownloadView extends AbstractFileDownloadView {

    @Override
    protected void addResponseHeader(Map<String, Object> model, HttpServletRequest request,
                                     HttpServletResponse response) {
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", model.get("csvFileName")));
        response.setContentType("text/plain");
    }

    @Override
    protected InputStream getInputStream(Map<String, Object> model, HttpServletRequest request) throws IOException {

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        Csv.save(model.get("exportCsvData"),
//                baos,
//                "UTF-8",
//                (CsvConfig) model.get("csvConfig"),
//                new CsvEntityListHandler<>((Class) model.get("class"))
////                (ColumnNameMappingBeanListHandler)model.get("handler")
//        );

        InputStream is = JacksonCsvUtils.writeTsvStream((Class) model.get("class"), model.get("exportCsvData"));

        return is;
    }
}
