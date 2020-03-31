package com.locus.assignment.service;

import com.locus.assignment.dto.ResourceDto;
import com.locus.assignment.entity.Resource;

public interface ResourceService {

    ResourceDto createResource(ResourceDto resourceDto);

    Resource findResourceById(Integer resourceId);

    void deleteResource(Integer resourceId);
}
