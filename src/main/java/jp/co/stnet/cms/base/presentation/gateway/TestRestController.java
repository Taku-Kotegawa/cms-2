package jp.co.stnet.cms.base.presentation.gateway;

import jp.co.stnet.cms.base.application.service.TestService;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * テスト用API
 * <pre>
 * 実行方法: (事前にユーザ管理でAPI-KEYの設定が必要)
 * <code>curl -i -X GET -H "Authorization:Bearer (API-KEY)" 'http://localhost:8080/api/test?a=aaa&b=bbb'</code>
 *
 * 結果:
 * Hello, demo. Parameters = {a=aaa, b=bbb}
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class TestRestController {

    private final TestService testService;

    @GetMapping("test")
    public Map<String, Object> test(Model model, @RequestParam Map<String, String> param, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        if (param.containsKey("exception")) {
            throw new IllegalArgumentException("exeption = " + param.get("exception"));
        } else if (param.containsKey("service-exception")) {
            testService.throwException();
        }

        var result = new LinkedHashMap<String, Object>();

        result.put("LocalDateTime", LocalDateTime.now());
        result.put("LocalDate", LocalDate.now());
        result.put("Instant.now", Instant.now());
        result.put("ZonedDateTime", ZonedDateTime.now());
        result.put("Date", new Date());

        return result;
    }

}
