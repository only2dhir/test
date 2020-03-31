package com.locus.assignment;

import com.locus.assignment.dao.ResourceDao;
import com.locus.assignment.service.AuthenticationFacadeService;
import com.locus.assignment.service.impl.ResourceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    @Mock
    private ResourceDao resourceDao;

    @Mock
    private AuthenticationFacadeService authenticationFacadeService;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Test
    public void createResourceTest() {

    }

    @Test
    public void findResourceTest() {

    }
}
