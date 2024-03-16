package jp.co.stnet.cms.common.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * クライアントのIPアドレスを取得するヘルパークラス。
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RemoteIpHelper {
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };
    private final HttpServletRequest request;

    public String getClinetRemoteIp() {

        if (request == null) {
            return "0.0.0.0";
        }

        for (String header: IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                // TODO WAF等自サイト側のリバースプロキシを除外する
                return ipList.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }

}
