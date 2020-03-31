package com.locus.assignment.controller;

import com.locus.assignment.dto.request.LoginRequest;
import com.locus.assignment.dto.response.ApiResponse;
import com.locus.assignment.dto.response.LoginResponse;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.util.Constants;
import com.locus.assignment.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<LoginResponse> register(@RequestBody LoginRequest loginRequest) {
        String token;
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JwtUtil.generateToken(authentication);
        }catch (AuthenticationException e){
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(), Constants.INVALID_CREDENTIALS);
        }
        LoginResponse loginResponse = new LoginResponse(token, System.currentTimeMillis());
        return new ApiResponse(HttpStatus.OK.value(), Constants.LOGIN_SUCCESS, loginResponse);
    }

}
