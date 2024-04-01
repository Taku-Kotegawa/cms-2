package jp.co.stnet.cms.base.domain.model;

import java.time.LocalDateTime;

public interface LastModifiedInterface {

    String getLastModifiedBy();

    LocalDateTime getLastModifiedDate();

    void setLastModifiedBy(String lastModifiedBy);

    void setLastModifiedDate(LocalDateTime lastModifiedDate);

}
