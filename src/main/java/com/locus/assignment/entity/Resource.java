package com.locus.assignment.entity;

import com.locus.assignment.dto.ResourceDto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Resource")
public class Resource extends BaseEntity {

    @Column(name = "ResourceName", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public ResourceDto toResourceDto(){
        ResourceDto dto = new ResourceDto();
        dto.setId(this.getId());
        dto.setName(this.name);
        return dto;
    }
}
