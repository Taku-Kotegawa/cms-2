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
public class CsvDownloadView extends AbstractFileDownloadView {

    @Override
    protected void addResponseHeader(Map<String, Object> model, HttpServletRequest request,
                                     HttpServletResponse response) {
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", model.get("csvFileName")));
        response.setContentType("text/plain");
    }

    @Override
    protected InputStream getInputStream(Map<String, Object> model, HttpServletRequest request) throws IOException {

        InputStream is = JacksonCsvUtils.writeStream((Class) model.get("class"), model.get("exportCsvData"));
        return is;
    }
}
