package com.example.demo.service.impl;

import com.example.demo.converter.ProjectConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ProjectService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    @SneakyThrows
    @Override
    public Project create(Project project) {
        try {
            return projectRepository.save(project);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Project update(Project project) {
        try {
            Project projectForUpdate = projectRepository.findById(project.getId()).orElseGet(Project::new);
            return projectRepository.save(ProjectConverter.projectConvertor(project, projectForUpdate));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Project getById(Long id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new BusinessException("Project with id " + id + " not found", null, HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            projectRepository.deleteById(id);
            return ResponseEntity.ok("Project with id: ".concat(id.toString()).concat(" has been remove"));
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Cannot find ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Project> getAll() {
        try {
            return (List<Project>) projectRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Error", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Project addPerson(Long personId, Long projectId) {
        try {
            Person person = personRepository.findById(personId).orElseGet(Person::new);
            Project project = projectRepository.findById(projectId).orElseGet(Project::new);

            Set<Project> projectSet = person.getProjects();
            projectSet.add(project);
            person.setProjects(projectSet);
            personRepository.save(person);

            Set<Person> personSet = project.getPersonList();
            personSet.add(person);
            project.setPersonList(personSet);
            return projectRepository.save(project);
        } catch (InvalidDataAccessApiUsageException | NullPointerException e) {
            throw new BusinessException("Incorrect ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
