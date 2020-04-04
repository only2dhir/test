package com.locus.assignment.controller;

import com.locus.assignment.annotation.Authorized;
import com.locus.assignment.dto.ResourceDto;
import com.locus.assignment.dto.response.ApiResponse;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.service.ResourceService;
import com.locus.assignment.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.locus.assignment.util.Constants.ROLE_ADMIN;
import static com.locus.assignment.util.Constants.ROLE_DELETE;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @Authorized(role = ROLE_ADMIN)
    @PostMapping
    public ApiResponse<ResourceDto> create(@RequestBody ResourceDto resourceDto){
        if(resourceDto.getName() == null){
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), Constants.MISSING_REQUIRED_FIELDS);
        }
        resourceDto = resourceService.createResource(resourceDto);
        return new ApiResponse<>(HttpStatus.OK.value(), Constants.SUCCESS, resourceDto);
    }

    @Authorized(role = {ROLE_ADMIN, ROLE_DELETE})
    @DeleteMapping("/{resourceId}")
    public ApiResponse deleteResource(@PathVariable Integer resourceId){
        resourceService.deleteResource(resourceId);
        return new ApiResponse(HttpStatus.OK.value(), Constants.RESOURCE_DELETED);
    }

}
