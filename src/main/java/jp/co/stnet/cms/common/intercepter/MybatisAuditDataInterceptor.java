package jp.co.stnet.cms.common.intercepter;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
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
import java.lang.reflect.Field;
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

    private static final String CREATE_USER = "createdBy";
    private static final String CREATE_DATE = "createdDate";
    private static final String UPDATE_USER = "lastModifiedBy";
    private static final String UPDATE_DATE = "lastModifiedDate";
    private static final String VERSION = "version";
//    private static final String GET = "get";
//    private static final String SET = "set";

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

            } else if (object instanceof MapperMethod.ParamMap paramMap) {
                log.debug("MapperMethod.ParamMap: " + object);

                if (paramMap.get("row") != null) {
                    setupUpdateInformation(username, localDateTimeNow, paramMap.get("row"));
                }

            } else if (object instanceof DefaultSqlSession.StrictMap) {
                log.debug("DefaultSqlSession.StrictMap: " + object);
                // todo パターン追加

            } else if (object instanceof Serializable) {
                if (SqlCommandType.INSERT == sqlCommandType) {
                    setupInsertInformation(username, localDateTimeNow, object);
                }
                setupUpdateInformation(username, localDateTimeNow, object);
            }

        }

        return invocation.proceed();
    }

    private void setupInsertInformation(String userName, LocalDateTime localDateTimeNow, Object dto)
            throws IllegalArgumentException {

        // 作成者の設定
        updateFiled(dto, CREATE_USER, String.class, userName);

        // 作成日時の設定
        updateFiled(dto, CREATE_DATE, LocalDateTime.class, localDateTimeNow);

        // バージョンの初期値設定
        updateFiled(dto, VERSION, Long.class, 1L);


        // リフレクションで dto の #setEnterUser() メソッド（enterUser のセッター）を取得する
//        Method setEnterUserMethod = ReflectionUtils.findMethod(dto.getClass(), SET_CREATE_USER, String.class);
//        if (Objects.nonNull(setEnterUserMethod)) {
        // dto の #setEnterUser() メソッドに userName をセットする
//            setEnterUserMethod.invoke(dto, userName);
//        }

        // リフレクションで dto の #setEnterDate() メソッド（enterDateのセッター）を取得する
//        Method setEnterDateMethod = ReflectionUtils.findMethod(dto.getClass(), SET_CREATE_DATE, LocalDateTime.class);
//        if (Objects.nonNull(setEnterDateMethod)) {
        // dto の #setEnterDate() メソッドに 現在時刻 をセットする
//            setEnterDateMethod.invoke(dto, localDateTimeNow);
//        }

        // リフレクションで dto の #setVersion() メソッドを取得する
//        Method setVersionMethod = ReflectionUtils.findMethod(dto.getClass(), SET_VERSION, Long.class);
//        if (Objects.nonNull(setVersionMethod)) {
        // dto の #setEnterDate() メソッドに 現在時刻 をセットする
//            setVersionMethod.invoke(dto, 1L);
//        }

    }


    private void setupUpdateInformation(String userName, LocalDateTime localDateTimeNow, Object dto)
            throws IllegalArgumentException {

        // 更新者の設定
        updateFiled(dto, UPDATE_USER, String.class, userName);

        // 更新日時の設定
        updateFiled(dto, UPDATE_DATE, LocalDateTime.class, localDateTimeNow);

//        Method setUpdateUserMethod = ReflectionUtils.findMethod(dto.getClass(), SET_UPDATE_USER, String.class);
//        if (Objects.nonNull(setUpdateUserMethod)) {
//            setUpdateUserMethod.invoke(dto, userName);
//        }
//
//        Method setUpdateDateMethod = ReflectionUtils.findMethod(dto.getClass(), SET_UPDATE_DATE,
//                LocalDateTime.class);
//        if (Objects.nonNull(setUpdateDateMethod)) {
//            setUpdateDateMethod.invoke(dto, localDateTimeNow);
//        }
    }


    private void updateFiled(Object dto, String fieldName, Class<?> type, Object value) {
        Field field = ReflectionUtils.findField(dto.getClass(), fieldName, type);
        if (field != null) {
            field.setAccessible(true);
            try {
                if (field.get(dto) != null) {
                    // 値がセットされていたら更新しない
                } else {
                    field.set(dto, value);
                }
            } catch (IllegalAccessException e) {
                // 握りつぶす
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

}


