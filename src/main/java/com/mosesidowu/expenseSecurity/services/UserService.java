package com.mosesidowu.expenseSecurity.services;


import com.mosesidowu.expenseSecurity.dtos.request.LogingUserRequest;
import com.mosesidowu.expenseSecurity.dtos.request.RegisterUserRequest;
import com.mosesidowu.expenseSecurity.dtos.response.LoginUserResponse;
import com.mosesidowu.expenseSecurity.dtos.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    LoginUserResponse login(LogingUserRequest logingUserRequest);

}
