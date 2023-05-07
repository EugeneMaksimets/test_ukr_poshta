package com.example.demo.service.impl;

import com.example.demo.converter.ProjectConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        Project projectForUpdate = projectRepository.findById(project.getId()).orElseGet(Project::new);
        return projectRepository.save(ProjectConverter.projectConvertor(project, projectForUpdate));
    }

    @Override
    public Project getById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElseGet(Project::new);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> getAll() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public Project addPerson(Long projectId, Long personId) {
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
    }

}
