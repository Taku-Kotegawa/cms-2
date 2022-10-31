package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.PasswordHistoryRepository;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PasswordHistoryServiceImpl implements PasswordHistoryService {

    private final PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public int insert(PasswordHistory history) {
        return passwordHistoryRepository.insert(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordHistory> findHistoriesByUseFrom(String username, LocalDateTime useFrom) {
        return passwordHistoryRepository.findByUsernameAndUseFromAfter(username, useFrom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordHistory> findLatest(String username, int limit) {
        return passwordHistoryRepository.findLatest(username);
    }
}
