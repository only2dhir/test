package com.locus.assignment.service;

import com.locus.assignment.security.AuthenticatedUser;

public interface AuthenticationFacadeService {

    AuthenticatedUser getAuthentication();

    void setAuthentication(AuthenticatedUser authentication);
}
