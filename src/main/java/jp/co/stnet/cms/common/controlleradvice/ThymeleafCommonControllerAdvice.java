package jp.co.stnet.cms.common.controlleradvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ThymeleafCommonControllerAdvice {
    @ModelAttribute("requestURI") // (1)
    public String requestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }
}