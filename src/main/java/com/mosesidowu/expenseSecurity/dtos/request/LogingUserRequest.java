package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.Data;

@Data
public class LogingUserRequest {

    private String email;
    private String password;

}
