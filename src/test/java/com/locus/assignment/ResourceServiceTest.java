package com.locus.assignment;

import com.locus.assignment.dao.ResourceDao;
import com.locus.assignment.dto.ResourceDto;
import com.locus.assignment.entity.Resource;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.impl.ResourceServiceImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    @Mock
    private ResourceDao resourceDao;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Test
    public void createResourceTest() {
        ResourceDto resource = new ResourceDto();
        resource.setName("resource1");
        Resource savedResource = new Resource();
        savedResource.setId(1);
        savedResource.setName("resource1");
        savedResource.setDefaultOnCreate();
        savedResource.setActive(true);
        when(resourceDao.save(any())).thenReturn(savedResource);
        resource = resourceService.createResource(resource);
        assertEquals(resource.getId(), savedResource.getId());
    }

    @Test(expected = ApiException.class)
    public void createResourceTest_ConstraintViolation() {
        ResourceDto resource = new ResourceDto();
        resource.setName("resource1");
        Resource savedResource = new Resource();
        savedResource.setId(1);
        savedResource.setName("resource1");
        savedResource.setDefaultOnCreate();
        savedResource.setActive(true);
        when(resourceDao.save(any())).thenThrow(ConstraintViolationException.class);
        resourceService.createResource(resource);
    }

    @Test
    public void findResourceTest() {
        Resource resource = new Resource();
        resource.setActive(true);
        resource.setName("resource");
        Optional<Resource> optionalResource = Optional.of(resource);
        when(resourceDao.findById(anyInt())).thenReturn(optionalResource);
        Resource resource1 = resourceService.findResourceById(1);
        assertEquals(resource1.getName(), resource.getName());
    }
}
