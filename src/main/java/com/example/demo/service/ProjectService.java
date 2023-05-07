package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.entity.Project;

import java.util.List;

public interface ProjectService {

    Project create(Project project);

    Project update(Project project);

    Project getById(Long id);

    void delete(Long id);

    List<Project> getAll();

    Project addPerson(Long projectId, Long personId);

}
