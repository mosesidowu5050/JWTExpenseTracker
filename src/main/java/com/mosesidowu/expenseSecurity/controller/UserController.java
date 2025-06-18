package com.mosesidowu.expenseSecurity.controller;


import com.mosesidowu.expenseSecurity.dtos.request.LogingUserRequest;
import com.mosesidowu.expenseSecurity.dtos.request.RegisterUserRequest;
import com.mosesidowu.expenseSecurity.dtos.response.ApiResponse;
import com.mosesidowu.expenseSecurity.dtos.response.LoginUserResponse;
import com.mosesidowu.expenseSecurity.dtos.response.RegisterUserResponse;
import com.mosesidowu.expenseSecurity.exception.UserException;
import com.mosesidowu.expenseSecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            RegisterUserResponse response = userService.register(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(response, true), CREATED);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogingUserRequest logingUserRequest) {
        try {
            LoginUserResponse response = userService.login(logingUserRequest);
            return new ResponseEntity<>(new ApiResponse(response, true), OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), BAD_REQUEST);
        }
    }
}
