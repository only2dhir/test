package com.locus.assignment;

import com.locus.assignment.dao.RoleDao;
import com.locus.assignment.dto.ResourceDto;
import com.locus.assignment.dto.RoleDto;
import com.locus.assignment.entity.Resource;
import com.locus.assignment.entity.Role;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.ResourceService;
import com.locus.assignment.service.impl.RoleServiceImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleDao roleDao;

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void createRoleTest() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("admin");
        roleDto.setDescription("admin");
        Role role = new Role();
        role.setName("admin");
        role.setId(1);
        role.setDescription("admin");
        when(roleDao.save(any())).thenReturn(role);
        when(roleDao.findByName(any())).thenReturn(null);
        roleDto = roleService.createRole(roleDto);
        assertEquals(roleDto.getId(), role.getId());
    }

    @Test(expected = ApiException.class)
    public void createRoleTest_ConstraintViolationException() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("admin");
        roleDto.setDescription("admin");
        Role role = new Role();
        role.setName("admin");
        role.setId(1);
        role.setDescription("admin");
        when(roleDao.findByName(any())).thenReturn(role);
        when(roleDao.save(any())).thenThrow(ConstraintViolationException.class);
        roleService.createRole(roleDto);
    }

    @Test
    public void findByIdTest() {
        Role role = new Role();
        role.setActive(true);
        role.setName("admin");
        role.setId(1);
        Optional<Role> optionalRole = Optional.of(role);
        when(roleDao.findById(anyInt())).thenReturn(optionalRole);
        Role role1 = roleService.findById(1);
        assertEquals(role.getName(), role1.getName());
    }

    @Test(expected = ApiException.class)
    public void findByIdTest_exception() {
        Role role = new Role();
        role.setActive(true);
        role.setName("admin");
        role.setId(1);
        when(roleDao.findAllById(any())).thenReturn(new ArrayList<>());
        roleService.findById(1);
    }

}
