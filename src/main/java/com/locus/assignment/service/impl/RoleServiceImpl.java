package com.locus.assignment.service.impl;

import com.locus.assignment.dao.RoleDao;
import com.locus.assignment.dto.RoleDto;
import com.locus.assignment.entity.Resource;
import com.locus.assignment.entity.Role;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.ResourceService;
import com.locus.assignment.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceService resourceService;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role existingRole = roleDao.findByName(roleDto.getName());
        if(existingRole != null){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), String.format("role with name %s already exists.", roleDto.getName()));
        }
        Role role = new Role(roleDto.getName(), roleDto.getDescription(), roleDto.getActionType());
        role.setDefaultOnCreate();
        roleDao.save(role);
        roleDto.setId(role.getId());
        return roleDto;
    }

    @Override
    public Role findById(Integer roleId) {
        List<Role> roles = findRolesByIds(Arrays.asList(roleId));
        if(ObjectUtils.isEmpty(roles)){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), String.format("Invalid role id %s", roleId));
        }
        return roles.get(0);
    }

    @Override
    public List<Role> findRolesByIds(List<Integer> roleIds) {
        return roleDao.findAllById(roleIds);
    }

    @Override
    public RoleDto assignRoleToResource(Integer roleId, Integer resourceId) {
        Resource resource = resourceService.findResourceById(resourceId);

        Role role = findById(roleId);
        Set<Resource> resourceSpecificRoles = role.getResources();
        if(ObjectUtils.isEmpty(resourceSpecificRoles)){
            resourceSpecificRoles = new HashSet<>();
        }
        role.setResources(resourceSpecificRoles);
        logger.info("Mapped resource %s with role %s", resource.getName(), role.getName());
        roleDao.save(role);
        return role.toRoleDto();

    }
}
