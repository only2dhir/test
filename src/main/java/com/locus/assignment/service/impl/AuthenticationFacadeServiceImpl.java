package com.locus.assignment.service.impl;

import com.locus.assignment.exception.ApiException;
import com.locus.assignment.security.AuthenticatedUser;
import com.locus.assignment.service.AuthenticationFacadeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.locus.assignment.util.Constants.TRY_LOGIN_AGAIN;

@Component
public class AuthenticationFacadeServiceImpl implements AuthenticationFacadeService {

    @Override
    public AuthenticatedUser getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AuthenticatedUser){
            return (AuthenticatedUser)authentication;
        }else {
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(), TRY_LOGIN_AGAIN);
        }
    }

    @Override
    public void setAuthentication(AuthenticatedUser authentication) {
        if(authentication instanceof AuthenticatedUser) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(), TRY_LOGIN_AGAIN);
        }
    }
}
