package com.locus.assignment.entity;

import com.locus.assignment.dto.RoleDto;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Roles")
public class Role extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ROLE_RESOURCE", joinColumns = {
            @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "RESOURCE_ID") })
    private Set<Resource> resources;

    public Role(){

    }

    public Role(String name, String description, ActionType actionType){
        this.name = name;
        this.description = description;
        this.actionType = actionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public RoleDto toRoleDto(){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(this.getId());
        roleDto.setActionType(this.actionType);
        roleDto.setName(this.name);
        if(!ObjectUtils.isEmpty(this.resources)) {
            roleDto.setResources(this.resources.stream().map(Resource::toResourceDto).collect(Collectors.toSet()));
        }
        return roleDto;
    }
}
