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
package jp.co.stnet.cms.base.presentation.interceptor;


import jp.co.stnet.cms.base.application.service.AccountSharedService;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PasswordExpirationCheckInterceptor implements HandlerInterceptor {

    @Inject
    AccountSharedService accountSharedService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {
        Authentication authentication = (Authentication) request
                .getUserPrincipal();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                LoggedInUser userDetails = (LoggedInUser) principal;
                if ((!userDetails.getAccount().getRoles().contains(Role.ADMIN.name()) && accountSharedService
                        .isCurrentPasswordExpired(userDetails.getUsername()))
                        || accountSharedService.isInitialPassword(userDetails
                        .getUsername())) {
                    response.sendRedirect(request.getContextPath()
                            + "/password?form");
                    return false;
                }
            }
        }

        return true;
    }
}
