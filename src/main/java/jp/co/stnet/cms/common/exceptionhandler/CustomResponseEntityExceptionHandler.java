package jp.co.stnet.cms.common.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ResponseEntityExceptionHandlerにアクセスするための中継用クラス
 */
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * protectedをpublicに変換する
     *
     * @param ex         the exception to handle
     * @param body       the body to use for the response
     * @param headers    the headers to use for the response
     * @param statusCode the status code to use for the response
     * @param request    the current request
     * @return エラーレスポンス
     */
    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
