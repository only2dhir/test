package com.locus.assignment.dao;

import com.locus.assignment.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ResourceDao extends JpaRepository<Resource, Integer> {

    @Query("select resource from Resource AS resource join resource.roles role where role.id IN :roleIds")
    Set<Resource> findAllByRoles(@Param("roleIds") List<Integer> roleIds);
}
