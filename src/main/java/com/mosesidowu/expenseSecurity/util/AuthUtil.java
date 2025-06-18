package com.mosesidowu.expenseSecurity.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

        public static String getCurrentUserEmail() {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
}
