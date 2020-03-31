package com.locus.assignment.dto;

import com.locus.assignment.entity.ActionType;

import java.util.Set;

public class RoleDto {

    private Integer id;
    private String name;
    private String description;
    private ActionType actionType;
    private Set<ResourceDto> resources;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceDto> resources) {
        this.resources = resources;
    }
}
