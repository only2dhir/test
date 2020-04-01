package com.locus.assignment;

import com.locus.assignment.dao.UserDao;
import com.locus.assignment.dto.RoleDto;
import com.locus.assignment.dto.UserDto;
import com.locus.assignment.entity.ActionType;
import com.locus.assignment.entity.Role;
import com.locus.assignment.entity.User;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.RoleService;
import com.locus.assignment.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setName("admin");
        userDto.setPassword("admin");
        userDto.setRoles(createRoleDtos());
        when(userDao.findByUsername("admin")).thenReturn(null);
        List<Role> roles = createRoles();
        when(roleService.findRolesByIds(any())).thenReturn(roles);
        when(userDao.save(any())).thenReturn(createUser());
        UserDto userDto1 = userService.createUser(userDto);
        assertEquals(userDto1.getName(), userDto.getName());
        assertEquals(userDto1.getId(), userDto.getId());
        assertEquals(userDto1.getRoles().size(), userDto.getRoles().size());
    }

    @Test(expected = ApiException.class)
    public void createUserTest_DuplicateUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setName("admin");
        userDto.setPassword("admin");
        userDto.setRoles(createRoleDtos());
        when(userDao.findByUsername("admin")).thenReturn(new User());
        userService.createUser(userDto);
    }

    @Test(expected = ApiException.class)
    public void createUserTest_EmptyRole() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setName("admin");
        userDto.setPassword("admin");
        userDto.setRoles(createRoleDtos());
        when(userDao.findByUsername("admin")).thenReturn(null);
        List<Role> roles = createRoles();
        when(roleService.findRolesByIds(any())).thenReturn(null);
        userService.createUser(userDto);
    }

    private User createUser() {
        User user = new User();
        user.setDefaultOnCreate();
        user.setPassword("abc");
        user.setName("admin");
        user.setUsername("admin");
        user.setRoles(new HashSet<>(createRoles()));
        return user;
    }

    private List<Role> createRoles() {
        Role firstRole = new Role("admin", "admin", ActionType.ADMIN);
        return Arrays.asList(firstRole);
    }


    private Set<RoleDto> createRoleDtos() {
        RoleDto roleDto = new RoleDto();
        roleDto.setDescription("admin");
        roleDto.setName("admin");
        roleDto.setActionType(ActionType.ADMIN);
        Set<RoleDto> roles = new HashSet<>();
        roles.add(roleDto);
        return roles;
    }

}
