/*
 * Copyright(c) 2013 NTT DATA Corporation. Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.co.stnet.cms.base.presentation.validation;


import jp.co.stnet.cms.base.application.service.AccountService;
import jp.co.stnet.cms.base.application.service.PasswordHistoryService;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.AccountRole;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistory;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class NotReusedPasswordValidator implements
        ConstraintValidator<NotReusedPassword, Object> {

    @Inject
    AccountService accountService;

    @Inject
    PasswordHistoryService passwordHistoryService;

    @Inject
    PasswordEncoder passwordEncoder;

    @Inject
    @Named("encodedPasswordHistoryValidator")
    PasswordValidator encodedPasswordHistoryValidator;

    @Value("${security.passwordHistoricalCheckingCount}")
    int passwordHistoricalCheckingCount;

    @Value("${security.passwordHistoricalCheckingPeriod}")
    int passwordHistoricalCheckingPeriod;

    private String usernamePropertyName;

    private String newPasswordPropertyName;

    private String message;

    @Override
    public void initialize(NotReusedPassword constraintAnnotation) {
        usernamePropertyName = constraintAnnotation.usernamePropertyName();
        newPasswordPropertyName = constraintAnnotation
                .newPasswordPropertyName();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        String username = (String) beanWrapper
                .getPropertyValue(usernamePropertyName);
        String newPassword = (String) beanWrapper
                .getPropertyValue(newPasswordPropertyName);

        newPassword = newPassword == null ? "" : newPassword;

        AccountRole accountRole = accountService.findById(username);
        String currentPassword = accountRole.getPassword();

        boolean result = checkNewPasswordDifferentFromCurrentPassword(
                newPassword, currentPassword, context);
        if (result && accountRole.getRoles().contains(Role.ADMIN.name())) {
            result = checkHistoricalPassword(username, newPassword, context);
        }

        return result;
    }

    private boolean checkNewPasswordDifferentFromCurrentPassword(
            String newPassword, String currentPassword,
            ConstraintValidatorContext context) {

        if (!passwordEncoder.matches(newPassword, currentPassword)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(newPasswordPropertyName)
                    .addConstraintViolation();
            return false;
        }
    }

    private boolean checkHistoricalPassword(String username,
                                            String newPassword, ConstraintValidatorContext context) {
        LocalDateTime useFrom = LocalDateTime.now()
                .minusMinutes(passwordHistoricalCheckingPeriod);
        List<PasswordHistory> historyByTime = passwordHistoryService
                .findHistoriesByUseFrom(username, useFrom);
        List<PasswordHistory> historyByCount = passwordHistoryService
                .findLatest(username, passwordHistoricalCheckingCount);
        List<PasswordHistory> history = historyByCount.size() > historyByTime
                .size() ? historyByCount : historyByTime;

        List<PasswordData.Reference> historyData = new ArrayList<>();
        for (PasswordHistory h : history) {
            historyData.add(new PasswordData.HistoricalReference(h
                    .getPassword()));
        }

        PasswordData passwordData = new PasswordData(newPassword);
        passwordData.setUsername(username);
        passwordData.setPasswordReferences(historyData);
        RuleResult result = encodedPasswordHistoryValidator
                .validate(passwordData);

        if (result.isValid()) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    encodedPasswordHistoryValidator.getMessages(result).get(0))
                    .addPropertyNode(newPasswordPropertyName)
                    .addConstraintViolation();
            return false;
        }
    }
}
