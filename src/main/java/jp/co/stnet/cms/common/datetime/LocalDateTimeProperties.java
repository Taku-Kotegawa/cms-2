package jp.co.stnet.cms.common.datetime;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LocalDateTimeProperties.PREFIX)
@Data
public class LocalDateTimeProperties {

    static final String PREFIX = "local-datetime-factory";

    private Type type = Type.current;
    private String fixTimestampQuery = "SELECT now FROM system_date";
    private String adjustedValueQuery= "SELECT diff * 60 * 1000 FROM operation_date";
    private Boolean useCache = false;

    enum Type {
        current,
        fix,
        adjusted
    };

}
