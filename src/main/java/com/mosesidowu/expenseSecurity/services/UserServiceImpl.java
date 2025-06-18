package com.mosesidowu.expenseSecurity.services;

import com.mosesidowu.expenseSecurity.data.models.User;
import com.mosesidowu.expenseSecurity.data.repository.UserRepository;
import com.mosesidowu.expenseSecurity.dtos.request.LogingUserRequest;
import com.mosesidowu.expenseSecurity.dtos.request.RegisterUserRequest;
import com.mosesidowu.expenseSecurity.dtos.response.LoginUserResponse;
import com.mosesidowu.expenseSecurity.dtos.response.RegisterUserResponse;
import com.mosesidowu.expenseSecurity.exception.UserException;
import com.mosesidowu.expenseSecurity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.mosesidowu.expenseSecurity.util.Mapper.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {

        User user = userMapper(passwordEncoder, registerUserRequest);
        userRepository.save(user);
        return userMapperResponse(user);
    }



    @Override
    public LoginUserResponse login(LogingUserRequest request) {
        try {
            // Authenticate with Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findUserByEmail(request.getEmail())
                    .orElseThrow(() -> new UserException("User not found"));

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail());
            LoginUserResponse loginUserResponse = new LoginUserResponse();
            loginUserResponse.setToken(token);
            loginUserResponse.setEmail(user.getEmail());
            loginUserResponse.setUserId(user.getUserId());
            loginUserResponse.setMessage("Login successful");

            return loginUserResponse;

        } catch (BadCredentialsException ex) {
            throw new UserException("Invalid email or password");
        }
    }

}
