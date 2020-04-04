package com.locus.assignment.aspect;

import com.locus.assignment.annotation.Authorized;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.AuthenticationFacadeService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class SecurityAspect {

    private final AuthenticationFacadeService authenticationFacadeService;

    public SecurityAspect(AuthenticationFacadeService authenticationFacadeService) {
        this.authenticationFacadeService = authenticationFacadeService;
    }

    @Before("@annotation(com.locus.assignment.annotation.Authorized)")
    public Object checkAccess(JoinPoint joinPoint) {
        String[] roles = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorized authorized = method.getAnnotation(Authorized.class);
        if (authorized != null) {
            roles = authorized.role();
        }

        boolean authorised = false;
        Authentication authentication = authenticationFacadeService.getAuthentication();
        if(authentication != null && authentication.getAuthorities() != null){
            List<String> finalRoles = Arrays.asList(roles);
            authorised = authentication.getAuthorities().stream().anyMatch(authority -> finalRoles.contains(authority.getAuthority()));
        }
        if (!authorised){
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "You are not authorised to access this resource");
        }
        return null;
    }
}
