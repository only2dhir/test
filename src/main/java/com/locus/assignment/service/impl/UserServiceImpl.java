package com.locus.assignment.service.impl;

import com.locus.assignment.dao.UserDao;
import com.locus.assignment.dto.RoleDto;
import com.locus.assignment.dto.UserDto;
import com.locus.assignment.entity.Role;
import com.locus.assignment.entity.User;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.RoleService;
import com.locus.assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.locus.assignment.util.Constants.*;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDto createUser(UserDto userDto) {
        User existingUser = userDao.findByUsername(userDto.getUsername());
        if(existingUser != null){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), String.format("Username %s already exists", userDto.getUsername()));
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());

        List<Role> roles = roleService.findRolesByIds(userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList()));
        if(ObjectUtils.isEmpty(roles) || roles.size() != userDto.getRoles().size()){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), INVALID_ROLE_SELECTED);
        }
        user.setRoles(new HashSet<>(roles));

        user.setDefaultOnCreate();
        userDao.save(user);
        logger.info(String.format("User %s created successfully.", userDto.getUsername()));
        return generateUserCreateResponse(user);
    }

    @Override
    public UserDto updateUserRoles(UserDto userDto) {
        User user = findUser(userDto.getId());
        if(!ObjectUtils.isEmpty(userDto.getRoles())){
            List<Role> newRoles = roleService.findRolesByIds(userDto.getRoles().stream().map(RoleDto :: getId).collect(Collectors.toList()));
            user.setRoles(new HashSet<>(newRoles));
        }
        user.setDefaultOnUpdate();
        userDao.save(user);
        return generateUserCreateResponse(user);
    }

    @Override
    public UserDto removeUserRole(Integer userId, Integer roleId) {
        User user = findUser(userId);
        roleService.findById(roleId);
        userDao.removeUserRole(userId, roleId);
        return generateUserCreateResponse(user);
    }

    @Override
    public UserDto addUserRole(Integer userId, Integer roleId) {
        User user = findUser(userId);
        roleService.findById(roleId);
        userDao.addUserRole(userId, roleId);
        return generateUserCreateResponse(user);
    }

    private UserDto generateUserCreateResponse(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        Set<Role> roles = user.getRoles();
        userDto.setRoles(roles.stream().map(Role :: toRoleDto).collect(Collectors.toSet()));
        return userDto;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(), INVALID_USERNAME_PASSWORD);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), INVALID_USERNAME);
        }
        return user;
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(ROLE_ + role.getActionType()));
        });
        return authorities;
    }

    private User findUser(Integer userId){
        Optional<User> optionalUser = userDao.findById(userId);
        if(!optionalUser.isPresent()){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), INVALID_USER_ID);
        }
        return optionalUser.get();
    }

}
