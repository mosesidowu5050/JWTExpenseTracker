package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String username;
    private String email;
    private String password;

}
