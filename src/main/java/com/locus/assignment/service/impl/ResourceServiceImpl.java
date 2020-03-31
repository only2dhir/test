package com.locus.assignment.service.impl;

import com.locus.assignment.dao.ResourceDao;
import com.locus.assignment.dto.ResourceDto;
import com.locus.assignment.entity.Resource;
import com.locus.assignment.entity.Role;
import com.locus.assignment.entity.User;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.AuthenticationFacadeService;
import com.locus.assignment.service.ResourceService;
import com.locus.assignment.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private AuthenticationFacadeService authenticationFacadeService;

    @Autowired
    private UserService userService;

    @Override
    public ResourceDto createResource(ResourceDto resourceDto) {
        Resource resource = new Resource();
        resource.setName(resourceDto.getName());
        resource.setDefaultOnCreate();
        try {
            resource = resourceDao.save(resource);
        }catch (ConstraintViolationException e){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), String.format("Resource with name %s already exists.", resourceDto.getName()));
        }
        resourceDto.setId(resource.getId());
        LOGGER.info(String.format("Resource %s created successfully", resourceDto.getName()));
        return resourceDto;
    }

    @Override
    public Resource findResourceById(Integer resourceId) {
        Optional<Resource> resourceOptional = resourceDao.findById(resourceId);
        if (!resourceOptional.isPresent()){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid resource id");
        }
        return resourceOptional.get();
    }

    private Set<Resource> findResourcesByRoleIds(List<Integer> roles) {
        return resourceDao.findAllByRoles(roles);
    }


    @Override
    public void deleteResource(Integer resourceId) {
        Resource resource = findResourceById(resourceId);
        String username = authenticationFacadeService.getAuthentication().getName();
        User user = userService.findByUsername(username);
        Set<Resource> resources = findResourcesByRoleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        boolean match = resources.stream().anyMatch(res -> res.getId().equals(resource.getId()));
        if(!match){
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(), String.format("Not authorised to delete the resource", resource.getName()));
        }
        resourceDao.delete(resource);
    }


}
