package com.example.demo.entity;

import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProjectTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    private Project project;
    private Person person1;
    private Person person2;

    @BeforeEach
    public void setUp() {
        person1 = new Person();
        person1.setFirstName("Name");
        person1.setLastName("Last Name");
        person1 = personRepository.save(person1);

        person2 = new Person();
        person2.setFirstName("Name2");
        person2.setLastName("Last Name2");
        person2 = personRepository.save(person2);

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        project = new Project();
        project.setName("Project 1");
        project.setDescription("Project description");
        project.setPersonList(persons);
    }

    @Test
    public void testSaveProject() {
        Project savedProject = projectRepository.save(project);

        Assertions.assertNotNull(savedProject.getId());
        Assertions.assertEquals("Project 1", savedProject.getName());
        Assertions.assertEquals("Project description", savedProject.getDescription());
        Assertions.assertEquals(2, savedProject.getPersonList().size());
    }

    @Test
    public void testGetProjectById() {
        projectRepository.save(project);

        Project retrievedProject = projectRepository.findById(project.getId()).orElse(null);

        Assertions.assertNotNull(retrievedProject);
        Assertions.assertEquals("Project 1", retrievedProject.getName());
        Assertions.assertEquals("Project description", retrievedProject.getDescription());
        Assertions.assertEquals(2, retrievedProject.getPersonList().size());
    }

    @Test
    public void testUpdateProject() {
        projectRepository.save(project);

        project.setName("New Project Name");
        project.setDescription("New Project Description");
        project.getPersonList().remove(person2);

        Project updatedProject = projectRepository.save(project);

        Assertions.assertEquals("New Project Name", updatedProject.getName());
        Assertions.assertEquals("New Project Description", updatedProject.getDescription());
        Assertions.assertEquals(1, updatedProject.getPersonList().size());
        Assertions.assertTrue(updatedProject.getPersonList().contains(person1));
        Assertions.assertFalse(updatedProject.getPersonList().contains(person2));
    }

    @Test
    public void testDeleteProject() {
        projectRepository.save(project);

        projectRepository.deleteById(project.getId());

        Project deletedProject = projectRepository.findById(project.getId()).orElse(null);

        Assertions.assertNull(deletedProject);
    }

    @Test
    public void testGetAllProjects() {
        projectRepository.save(project);

        Project project2 = new Project();
        project2.setName("Project 2");
        project2.setDescription("Project 2 description");
        projectRepository.save(project2);

        List<Project> projects = (List<Project>) projectRepository.findAll();

        Assertions.assertEquals(2, projects.size());
    }
}
