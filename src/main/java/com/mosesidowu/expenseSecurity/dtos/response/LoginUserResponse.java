package com.mosesidowu.expenseSecurity.dtos.response;

import lombok.Data;

@Data
public class LoginUserResponse {

    private String userId;
    private String email;
    private String message;
    private String token;

}
