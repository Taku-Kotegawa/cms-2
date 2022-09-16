package jp.co.stnet.cms.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class DataIntegrityViolationBusinessException extends BusinessException {
    public DataIntegrityViolationBusinessException(ResultMessages messages) {
        super(messages);
    }
}
