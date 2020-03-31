package com.locus.assignment.dao;

import com.locus.assignment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByName(String roleName);
}
