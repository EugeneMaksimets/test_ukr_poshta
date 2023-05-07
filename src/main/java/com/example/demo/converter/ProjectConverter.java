package com.example.demo.converter;

import com.example.demo.entity.Project;

public class ProjectConverter {
    public static Project projectConvertor(Project projectWithNewInfo, Project projectToUpdate) {
        projectToUpdate.setDescription(projectWithNewInfo.getDescription());
        projectToUpdate.setName(projectWithNewInfo.getName());
        return projectToUpdate;
    }
}
