package jp.co.stnet.cms.common.intercepter;

import jp.co.stnet.cms.base.domain.model.StatusInterface;
import jp.co.stnet.cms.base.domain.model.authentication.LoggedInUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 監査カラムに自動的に値をセットするMyBatisプラグイン
 *
 * 対応しているフィールド: CreatedBy, CreatedDate, LastModifiedBy, LastModifiedDate, version
 * ユーザ名はSpringSecurityに対応、無認証時は"anonymous"
 *
 * 注意：すべてのパターンに対応していない。
 */
@Slf4j
@Component("mybatisAuditDataInterceptor")
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class MybatisAuditDataInterceptor implements Interceptor {

    private static final String SET_CREATE_USER = "setCreatedBy";
    private static final String SET_CREATE_DATE = "setCreatedDate";
    private static final String SET_UPDATE_USER = "setLastModifiedBy";
    private static final String SET_UPDATE_DATE = "setLastModifiedDate";

    private static final String SET_VERSION = "setVersion";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // ログインユーザの情報取得
        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LoggedInUser loggedInUser = (LoggedInUser) authentication.getPrincipal();
            username = (loggedInUser != null) ? loggedInUser.getUsername() : "anonymous";
        } else {
            username = "anonymous";
        }

        // 登録、更新日時にセットしたい時刻を変数にいれる
        // Todo 共通の時刻取得処理から取得する方法に変更
        LocalDateTime localDateTimeNow = LocalDateTime.now();

        // insert or update
        SqlCommandType sqlCommandType = null;

        Object[] args = invocation.getArgs();

        for (Object object : args) {
            // Get operation type from MappedStatement parameter
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                sqlCommandType = ms.getSqlCommandType();
                continue;

            } else if (object instanceof Serializable) {
                if (SqlCommandType.INSERT == sqlCommandType) {
                    setupInsertInformation(username, localDateTimeNow, object);
                }
                setupUpdateInformation(username, localDateTimeNow, object);

            } else if (object instanceof MapperMethod.ParamMap) {
                log.debug("MapperMethod.ParamMap: " + object.toString());
                // todo パターン追加


            } else if (object instanceof DefaultSqlSession.StrictMap) {
                log.debug("DefaultSqlSession.StrictMap: " + object.toString());
                // todo パターン追加

            }

        }

        return invocation.proceed();
    }

    private void setupInsertInformation(String userName, LocalDateTime localDateTimeNow, Object dto)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // リフレクションで dto の #setEnterUser() メソッド（enterUser のセッター）を取得する
        Method setEnterUserMethod = ReflectionUtils.findMethod(dto.getClass(), SET_CREATE_USER, String.class);
        if (Objects.nonNull(setEnterUserMethod)) {
            // dto の #setEnterUser() メソッドに userName をセットする
            setEnterUserMethod.invoke(dto, userName);
        }

        // リフレクションで dto の #setEnterDate() メソッド（enterDateのセッター）を取得する
        Method setEnterDateMethod = ReflectionUtils.findMethod(dto.getClass(), SET_CREATE_DATE, LocalDateTime.class);
        if (Objects.nonNull(setEnterDateMethod)) {
            // dto の #setEnterDate() メソッドに 現在時刻 をセットする
            setEnterDateMethod.invoke(dto, localDateTimeNow);
        }

        // リフレクションで dto の #setVersion() メソッドを取得する
        Method setVersionMethod = ReflectionUtils.findMethod(dto.getClass(), SET_VERSION, Long.class);
        if (Objects.nonNull(setVersionMethod)) {
            // dto の #setEnterDate() メソッドに 現在時刻 をセットする
            setVersionMethod.invoke(dto, 1L);
        }

    }


    private void setupUpdateInformation(String userName, LocalDateTime localDateTimeNow, Object dto)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method setUpdateUserMethod = ReflectionUtils.findMethod(dto.getClass(), SET_UPDATE_USER, String.class);
        if (Objects.nonNull(setUpdateUserMethod)) {
            setUpdateUserMethod.invoke(dto, userName);
        }

        Method setUpdateDateMethod = ReflectionUtils.findMethod(dto.getClass(), SET_UPDATE_DATE,
                LocalDateTime.class);
        if (Objects.nonNull(setUpdateDateMethod)) {
            setUpdateDateMethod.invoke(dto, localDateTimeNow);
        }
    }

}


