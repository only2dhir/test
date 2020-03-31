package com.locus.assignment.service;

import com.locus.assignment.dto.UserDto;
import com.locus.assignment.entity.User;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUserRoles(UserDto userDto);

    UserDto removeUserRole(Integer userId, Integer roleId);

    UserDto addUserRole(Integer userId, Integer roleId);

    User findByUsername(String username);
}
