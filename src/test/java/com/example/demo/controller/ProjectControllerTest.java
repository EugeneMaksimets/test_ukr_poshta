package com.example.demo.controller;


import com.example.demo.converter.ProjectConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private Project project2;
    private List<Project> projects;

    @BeforeEach
    public void setUp() {

        project = new Project();
        project.setId(1L);
        project.setName("Name");
        project.setDescription("description");

        project2 = new Project();
        project2.setId(2L);
        project2.setName("Name2");
        project2.setDescription("description");

        projects = new ArrayList<>();

    }

    @Test
    void createTest() {
        when(projectService.create(project)).thenReturn(project);

        Project createdProject = projectController.create(project);

        verify(projectService).create(project);
        verify(projectService, times(1)).create(project);
        assertEquals(project, createdProject);
    }

    @Test
    public void updateTest() {
        Project updatedProject = ProjectConverter.projectConvertor(project2, project);

        projectController.update(updatedProject);

        assertEquals(project2.getName(), updatedProject.getName());
        assertEquals(project2.getDescription(), updatedProject.getDescription());
        assertEquals(project.getId(), updatedProject.getId());
        assertNotEquals(project2.getId(), project.getId());
    }

    @Test
    public void getByIdTest() {
        when(projectService.getById(project.getId())).thenReturn(project);

        Project foundProject = projectController.getById(1L);

        verify(projectService, times(1)).getById(any());
        verify(projectService).getById(project.getId());
        assertEquals(project, foundProject);
    }

    @Test
    public void deleteTest() {
        projectController.delete(project.getId());

        verify(projectService, times(1)).delete(any());
        verify(projectService).delete(project.getId());
    }

    @Test
    public void getAllTest() {
        when(projectService.getAll()).thenReturn(projects);

        List<Project> result = projectController.getAll();

        assertEquals(projects, result);
        verify(projectService, times(1)).getAll();
    }


}
