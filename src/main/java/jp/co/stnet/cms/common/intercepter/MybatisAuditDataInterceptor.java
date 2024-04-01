package jp.co.stnet.cms.common.intercepter;

import jp.co.stnet.cms.base.domain.model.CreatedInterface;
import jp.co.stnet.cms.base.domain.model.LastModifiedInterface;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/**
 * 監査カラムに自動的に値をセットするMyBatisプラグイン
 * <p>
 * 対応しているフィールド: CreatedBy, CreatedDate, LastModifiedBy, LastModifiedDate, version
 * ユーザ名はSpringSecurityに対応、無認証時は"anonymous"
 * <p>
 * 注意：すべてのパターンに対応していない。
 */
@Slf4j
@Component("mybatisAuditDataInterceptor")
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class MybatisAuditDataInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // ログインユーザの情報取得
        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                LoggedInUser loggedInUser = (LoggedInUser) authentication.getPrincipal();
                username = (loggedInUser != null) ? loggedInUser.getUsername() : "anonymous";
            } catch (ClassCastException ex) {
                username = "anonymous";
            }
        } else {
            username = "anonymous";
        }

        // 登録、更新日時にセットしたい時刻を変数にいれる
        // Todo 共通の時刻取得処理から取得する方法に変更
        LocalDateTime localDateTimeNow = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        // insert or update
        SqlCommandType sqlCommandType = null;

        Object[] args = invocation.getArgs();

        for (Object object : args) {
            // Get operation type from MappedStatement parameter
            if (object instanceof MappedStatement ms) {
                sqlCommandType = ms.getSqlCommandType();

            } else {

                if (object instanceof CreatedInterface && SqlCommandType.INSERT == sqlCommandType) {
                    ((CreatedInterface) object).setCreatedBy(username);
                    ((CreatedInterface) object).setCreatedDate(localDateTimeNow);
                }

                if (object instanceof LastModifiedInterface) {
                    ((LastModifiedInterface) object).setLastModifiedBy(username);
                    ((LastModifiedInterface) object).setLastModifiedDate(localDateTimeNow);
                }

                if (object instanceof VersionInterface && SqlCommandType.INSERT == sqlCommandType) {
                    ((VersionInterface) object).setVersion(1L);
                }

            }
        }

        return invocation.proceed();
    }

}


