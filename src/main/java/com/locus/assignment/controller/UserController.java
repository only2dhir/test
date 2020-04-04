package com.locus.assignment.controller;

import com.locus.assignment.annotation.Authorized;
import com.locus.assignment.dto.UserDto;
import com.locus.assignment.dto.response.ApiResponse;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import static com.locus.assignment.util.Constants.*;

@RequestMapping("/users")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Authorized(role = ROLE_ADMIN)
    @PostMapping
    public ApiResponse<UserDto> create(@RequestBody UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), MISSING_REQUIRED_FIELDS);
        }
        if (ObjectUtils.isEmpty(userDto.getRoles())) {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), ROLE_SELECTION_REQUIRED);
        }
        UserDto userResponse = userService.createUser(userDto);
        return new ApiResponse<>(HttpStatus.OK.value(), USER_CREATED, userResponse);
    }

    @Authorized(role = ROLE_ADMIN)
    @PutMapping
    public ApiResponse<UserDto> updateUserRoles(@RequestBody UserDto userDto) {
        if (userDto.getId() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), USER_ID_REQUIRED);
        }
        UserDto userResponse = userService.updateUserRoles(userDto);
        return new ApiResponse<>(HttpStatus.OK.value(), USER_UPDATED_SUCCESS, userResponse);
    }

    @Authorized(role = ROLE_ADMIN)
    @DeleteMapping("/{userId}/role/{roleId}")
    public ApiResponse<UserDto> removeUserRole(@PathVariable Integer roleId, @PathVariable Integer userId) {
        UserDto userResponse = userService.removeUserRole(userId, roleId);
        return new ApiResponse<>(HttpStatus.OK.value(), USER_ROLE_REMOVED, userResponse);
    }

    @Authorized(role = ROLE_ADMIN)
    @PutMapping("/{userId}/role/{roleId}")
    public ApiResponse<UserDto> addUserRole(@PathVariable Integer roleId, @PathVariable Integer userId) {
        UserDto userResponse = userService.addUserRole(userId, roleId);
        return new ApiResponse<>(HttpStatus.OK.value(), USER_ROLE_ADDED, userResponse);
    }

}
