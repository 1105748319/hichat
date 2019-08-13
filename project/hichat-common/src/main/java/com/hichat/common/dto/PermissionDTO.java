package com.hichat.common.dto;

import java.util.ArrayList;
import java.util.List;

public class PermissionDTO {

    private Long id;

    private String label;

    private List<PermissionDTO> children = new ArrayList<PermissionDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<PermissionDTO> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionDTO> children) {
        this.children = children;
    }
}
