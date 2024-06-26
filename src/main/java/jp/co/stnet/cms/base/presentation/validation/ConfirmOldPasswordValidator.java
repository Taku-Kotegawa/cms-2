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
import jp.co.stnet.cms.base.domain.model.Account;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmOldPasswordValidator implements
        ConstraintValidator<ConfirmOldPassword, Object> {

    @Inject
    AccountService accountService;

    @Inject
    PasswordEncoder passwordEncoder;

    private String usernamePropertyName;

    private String oldPasswordPropertyName;

    private String message;

    @Override
    public void initialize(ConfirmOldPassword constraintAnnotation) {
        usernamePropertyName = constraintAnnotation.usernamePropertyName();
        oldPasswordPropertyName = constraintAnnotation.oldPasswordPropertyName();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        String username = (String) beanWrapper.getPropertyValue(usernamePropertyName);
        String oldPassword = (String) beanWrapper
                .getPropertyValue(oldPasswordPropertyName);

        oldPassword = oldPassword == null ? "" : oldPassword;
        Account account = accountService.findById(username);
        String currentPassword = account.getPassword();

        return checkOldPasswordMacheWithCurrentPassword(oldPassword,
                currentPassword, context);
    }

    private boolean checkOldPasswordMacheWithCurrentPassword(
            String oldPassword, String currentPassword,
            ConstraintValidatorContext context) {
        if (passwordEncoder.matches(oldPassword, currentPassword)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(oldPasswordPropertyName).addConstraintViolation();
            return false;
        }
    }

}
