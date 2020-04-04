package com.locus.assignment.controller;

import com.locus.assignment.annotation.Authorized;
import com.locus.assignment.dto.RoleDto;
import com.locus.assignment.dto.response.ApiResponse;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.RoleService;
import com.locus.assignment.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.locus.assignment.util.Constants.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Authorized(role = {ROLE_ADMIN})
    @PostMapping
    public ApiResponse<RoleDto> create(@RequestBody RoleDto role){
        if(role.getName() == null || role.getActionType() == null){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), Constants.MISSING_REQUIRED_FIELDS);
        }
        logger.debug(String.format("Request to add a new role '%s'", role.getName()));
        role = roleService.createRole(role);
        return new ApiResponse<>(HttpStatus.OK.value(), ROLE_CREATED_SUCCESSFULLY, role);
    }

    @Authorized(role = ROLE_ADMIN)
    @PutMapping("/{roleId}/assign/{resourceId}")
    public ApiResponse<RoleDto> assignResourceToRole(@PathVariable Integer roleId, @PathVariable Integer resourceId){
        logger.debug(String.format("Request to map resource %s to role %s", resourceId, roleId));
        RoleDto roleDto = roleService.assignRoleToResource(roleId, resourceId);
        return new ApiResponse(HttpStatus.OK.value(), MAP_RESOURCE_ROLE, roleDto);
    }
}
