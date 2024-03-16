package jp.co.stnet.cms.common.datetime;

import java.time.LocalDateTime;

public interface LocalDateTimeFactory {

    /**
     * システム時刻を取得する。
     *
     * appliaction.propertiesの設定により修正可能。
     * <p>
     * local-datetime-factory.type:<br>
     *  - default: システム時刻<br>
     *  - fix: 指定したSQLの実行結果の値<br>
     *  - adjusted: 指定したSQLの実行結果のシステム時刻を加算減算した値(単位:分)<br>
     * local-datetime-factory.fix-timestamp-query: type=fixの場合に実行されるSQL(日時型を返すこと)<br>
     * local-datetime-factory.adjusted-value-query: type=adjustedの場合に実行されるSQL(分単位の整数を返すこと、マイナス可)<br>
     * local-datetime-factory.use-cache: true=fix or adjustedを指定した場合、DBから取得した値をキャッシュする、false=キャッシュしない
     *
     * @return システム時刻
     */
    LocalDateTime now();


    /**
     * キャッシュをクリアする
     */
    void clearCache();
}
