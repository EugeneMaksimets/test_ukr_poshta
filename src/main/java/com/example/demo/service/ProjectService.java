package com.example.demo.service;

import com.example.demo.entity.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    Project create(Project project);

    Project update(Project project);

    Project getById(Long id);

    ResponseEntity<?> delete(Long id);

    List<Project> getAll();

    Project addPerson(Long projectId, Long personId);

}
