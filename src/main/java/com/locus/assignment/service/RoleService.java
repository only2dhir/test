package com.locus.assignment.service;

import com.locus.assignment.dto.RoleDto;
import com.locus.assignment.entity.Role;

import java.util.List;

public interface RoleService {

    RoleDto createRole(RoleDto role);

    Role findById(Integer roleId);

    List<Role> findRolesByIds(List<Integer> roleIds);

    RoleDto assignRoleToResource(Integer roleId, Integer resourceId);
}
