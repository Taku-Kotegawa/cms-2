package jp.co.stnet.cms.common.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * APIコントローラで例外が発生した場合、RFC7807形式のエラーレスポンスを返す。
 * <pre>
 *     Web画面とWebAPIを１つにアプリケーションサーバ上で混在させた場合に、
 *     標準機能のspring.mvc.problemdetails.enabledを使うと、Web画面にも影響が及ぶ。
 *     本ハンドラーは、/api/** のURLのアクセスした場合のみ、RFC7807に変換する。
 *
 *     RFC7807のエラーレスポンスの作成はResponseEntityExceptionHandlerを使っているが、
 *     protectedなメソッドにアクセスするため、CustomResponseEntityExceptionHandler
 *     を作成している。
 *
 *     ResponseEntityExceptionHandlerで対象になっていない例外発生時もRFC7807を返す
 *     ようにしている。
 *
 *     本機能を有効化した場合でも、SpringSecurityが発生させるAccessDeniedException
 *     などの一部の例外はRFC7807に変換できない。
 *
 * </pre>
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    private final CustomResponseEntityExceptionHandler problemDetailsExceptionHandler = new CustomResponseEntityExceptionHandler();

    private final static String API_URI = "uri=/api/";

    /**
     * Exceptionがthrowされた場合の操作
     *
     * @param ex      例外
     * @param request WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleSystemError(Exception ex, WebRequest request) throws Exception {

        if (!request.getDescription(false).startsWith(API_URI)) {
            throw ex;
        }

        // エラーのスタックトレースを表示
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        String trace = sw.toString().replaceAll("\n|\t", " ");

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problemDetail = ProblemDetail.forStatus(status);
        var msg = ex.getLocalizedMessage();
        problemDetail.setDetail(msg);
        problemDetail.setProperty("trace", trace);

        log.error(msg, ex);

        return problemDetailsExceptionHandler.handleExceptionInternal(ex, problemDetail, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            ServletRequestBindingException.class,
            MethodArgumentNotValidException.class,
            HandlerMethodValidationException.class,
            NoHandlerFoundException.class,
            NoResourceFoundException.class,
            AsyncRequestTimeoutException.class,
            ErrorResponseException.class,
            MaxUploadSizeExceededException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodValidationException.class,
            BindException.class
    })
    public ResponseEntity<Object> customHandleException(Exception ex, WebRequest request) throws Exception {

        if (!request.getDescription(false).startsWith(API_URI)) {
            throw ex;
        }
        return problemDetailsExceptionHandler.handleException(ex, request);
    }


}
