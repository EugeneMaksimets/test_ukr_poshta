package com.example.demo.controller;

import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/project/create")
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PutMapping("/project/update")
    public Project update(@RequestBody Project project) {
        return projectService.update(project);
    }

    @PutMapping("/project/add/person")
    public Project addPerson(@RequestParam Long id, @RequestParam Long idProject) {
        return projectService.addPerson(id, idProject);
    }

    @DeleteMapping("/project/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return projectService.delete(id);
    }

    @GetMapping("/project")
    public Project getById(@RequestParam Long id) {
        return projectService.getById(id);
    }

    @GetMapping("/project/all")
    public List<Project> getAll() {
        return projectService.getAll();
    }
}
