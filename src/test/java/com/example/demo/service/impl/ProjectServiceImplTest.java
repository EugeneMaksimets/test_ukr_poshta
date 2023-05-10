package com.example.demo.service.impl;

import com.example.demo.converter.ProjectConverter;
import com.example.demo.entity.Project;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private Project project2;

    @BeforeEach
    public void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Name");
        project.setDescription("description");

        project2 = new Project();
        project2.setId(2L);
        project2.setName("Name2");
        project2.setDescription("description2");
    }

    @Test
    public void createTest() {
        when(projectRepository.save(project)).thenReturn(project);

        Project createdPerson = projectService.create(project);

        assertEquals(project, createdPerson);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void createTestThrowException() {
        when(projectRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> projectService.create(project),
                "Expected create() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void updateTest() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project);

        Project updateProject = ProjectConverter.projectConvertor(project2, project);

        projectService.update(updateProject);

        verify(projectRepository, times(1)).findById(any());
        verify(projectRepository, times(1)).save(any());
        assertEquals(project2.getName(), updateProject.getName());
        assertEquals(project2.getDescription(), updateProject.getDescription());
        assertEquals(project.getId(), updateProject.getId());
    }

    @Test
    public void updateTestThrowException() {
        when(projectRepository.findById(any())).thenThrow(InvalidDataAccessApiUsageException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> projectService.update(project),
                "Expected update() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void getByIdTest() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        Project foundProject = projectService.getById(1L);

        verify(projectRepository, times(1)).findById(project.getId());
        verify(projectRepository).findById(project.getId());
        assertEquals(project, foundProject);
    }

    @Test
    public void deleteTest() {
        projectService.delete(project.getId());

        verify(projectRepository, times(1)).deleteById(project.getId());
        verify(projectRepository).deleteById(project.getId());
    }

    @Test
    public void deleteTest_LevelNotFound() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(projectRepository).deleteById(id);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> projectService.delete(id),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Cannot find ID", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());

    }

    @Test
    public void getAllTest() {
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        projectList.add(project2);

        when(projectRepository.findAll()).thenReturn(projectList);

        List<Project> result = projectService.getAll();

        assertEquals(projectList, result);
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest_Exception() {
        when(projectRepository.findAll()).thenThrow(RuntimeException.class);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> projectService.getAll(),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Error", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }
}
