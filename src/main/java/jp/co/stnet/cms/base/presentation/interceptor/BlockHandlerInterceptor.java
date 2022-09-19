package jp.co.stnet.cms.base.presentation.interceptor;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BlockHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (ex != null) {
            return;
        }

        // staticファイルはインターセプトさせずにスルーさせる
        if (handler instanceof ResourceHttpRequestHandler) {
            return;
        }

//        String[] noCountUrl = {""};
//        if (response.getContentType() != null && response.getContentType().contains("text/html")) {
//            accessCounterService.countUp(request.getRequestURI());
//        }

    }

}
