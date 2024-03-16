package jp.co.stnet.cms.base.infrastructure.mapper;


import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailSendHistoryQueryMapper {

    List<MailSendHistory> selectNewest();

}
