package com.locus.assignment;

import com.locus.assignment.dao.RoleDao;
import com.locus.assignment.service.ResourceService;
import com.locus.assignment.service.impl.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    }

    @Test
    public void findByIdTest() {

    }

    @Test
    public void assignRoleToResourceTest(){

    }
}
