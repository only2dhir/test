package com.locus.assignment;

import com.locus.assignment.dao.UserDao;
import com.locus.assignment.service.RoleService;
import com.locus.assignment.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private BCryptPasswordEncoder bcryptEncoder;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUserTest() {

    }

    @Test
    public void updateUserRolesTest() {

    }

    @Test
    public void removeUserRoleTest() {

    }

}
