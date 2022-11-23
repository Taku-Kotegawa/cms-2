package jp.co.stnet.cms.common.datetime;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@EnableConfigurationProperties(LocalDateTimeProperties.class)
public class LocalDateTimeFactoryImpl implements LocalDateTimeFactory {

    private final LocalDateTimeProperties properties;
    private final JdbcTemplate jdbcTemplate;

    // クラス変数にキャッシュを持つ
    private Long cachedDiff;
    private Timestamp cachedFix;

    @Override
    public LocalDateTime now() {
        return switch (properties.getType()) {
            case current -> currectDateTime();
            case fix -> fixDateTime();
            case adjusted -> adjustedDateTime();
        };
    }

    @Override
    public void clearCache() {
        cachedDiff = null;
        cachedFix = null;
    }

    private LocalDateTime currectDateTime() {
        return LocalDateTime.now();
    }

    private LocalDateTime fixDateTime() {
        Timestamp fixTime;
        if (properties.getUseCache()) {
            if (cachedFix == null) {
                cachedFix = getFixTime();
            }
            fixTime = cachedFix;
        } else {
            fixTime = getFixTime();
        }
        return fixTime.toLocalDateTime();
    }

    private Timestamp getFixTime() {
        return jdbcTemplate.queryForObject(properties.getFixTimestampQuery(), Timestamp.class);
    }

    private LocalDateTime adjustedDateTime() {
        Long diff;
        if (properties.getUseCache()) {
            if (cachedDiff == null) {
                cachedDiff = getDiff();
            }
            diff = cachedDiff;
        } else {
            diff = getDiff();
        }
        return LocalDateTime.now().plusMinutes(diff);
    }

    private Long getDiff() {
        return jdbcTemplate.queryForObject(properties.getAdjustedValueQuery(), Long.class);
    }

}
