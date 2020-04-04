package com.locus.assignment.util;

import com.locus.assignment.dao.RoleDao;
import com.locus.assignment.dao.UserDao;
import com.locus.assignment.entity.ActionType;
import com.locus.assignment.entity.Role;
import com.locus.assignment.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DemoData implements CommandLineRunner {

    @Autowired
    private final UserDao userDao;

    @Autowired
    private final RoleDao roleDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DemoData(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    @Override
    public void run(String...args) {
        Role role = new Role();
        role.setName("admin");
        role.setDescription("Admin");
        role.setDescription("Admin");
        role.setActionType(ActionType.ADMIN);
        role = roleDao.save(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User();
        user.setUsername("admin");
        user.setName("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        userDao.save(user);
        user.setRoles(roles);
        userDao.save(user);
    }
}