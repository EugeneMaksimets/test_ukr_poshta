package com.example.demo.entity;

import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.entity.Role;
import com.example.demo.entity.Team;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Person person;
    private Project project1;
    private Project project2;
    private Level level;
    private Level level2;
    private Role role;
    private Role role2;
    private Team team;
    private Team team2;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setName("developer");
        roleRepository.save(role);

        role2 = new Role();
        role2.setName("QA");
        roleRepository.save(role2);

        team = new Team();
        team.setName("Some Team");
        teamRepository.save(team);

        team2 = new Team();
        team2.setName("Team2");
        teamRepository.save(team2);

        level = new Level();
        level.setName("junior");
        levelRepository.save(level);

        level2 = new Level();
        level2.setName("senior");
        levelRepository.save(level2);

        project1 = new Project();
        project1.setName("Project");
        projectRepository.save(project1);

        project2 = new Project();
        project2.setName("Project2");
        projectRepository.save(project2);

        Set<Project> projects = new HashSet<>();
        projects.add(project1);
        projects.add(project2);

        person = new Person();
        person.setFirstName("Name");
        person.setLastName("Last Name");
        person.setTeam(team);
        person.setRole(role);
        person.setLevel(level);
        person.setProjects(projects);

    }

    @Test
    public void testSavePerson() {
        Person savedPerson = personRepository.save(person);

        Assertions.assertNotNull(savedPerson.getId());
        Assertions.assertEquals("Name", savedPerson.getFirstName());
        Assertions.assertEquals("Last Name", savedPerson.getLastName());
        Assertions.assertEquals("junior", savedPerson.getLevel().getName());
        Assertions.assertEquals("developer", savedPerson.getRole().getName());
        Assertions.assertEquals(2, savedPerson.getProjects().size());
    }

    @Test
    public void testGetPersonById() {
        personRepository.save(person);

        Person savedPerson = personRepository.findById(person.getId()).orElse(null);

        Assertions.assertNotNull(savedPerson);
        Assertions.assertEquals("Name", savedPerson.getFirstName());
        Assertions.assertEquals("Last Name", savedPerson.getLastName());
        Assertions.assertEquals(2, savedPerson.getProjects().size());
    }

    @Test
    public void testUpdatePerson() {
        personRepository.save(person);

        person.setFirstName("New Name");
        person.setLastName("New Last Name");
        person.getProjects().remove(project2);
        person.setTeam(team2);
        person.setLevel(level2);
        person.setRole(role2);

        Person updatePerson = personRepository.save(person);

        Assertions.assertEquals("New Name", updatePerson.getFirstName());
        Assertions.assertEquals("New Last Name", updatePerson.getLastName());
        Assertions.assertEquals(1, updatePerson.getProjects().size());
        Assertions.assertEquals(team2.getName(), updatePerson.getTeam().getName());
        Assertions.assertEquals(level2.getName(), updatePerson.getLevel().getName());
        Assertions.assertEquals(role2.getName(), updatePerson.getRole().getName());
        Assertions.assertTrue(updatePerson.getProjects().contains(project1));
        Assertions.assertFalse(updatePerson.getProjects().contains(project2));
    }

    @Test
    public void testDeletePerson() {
        personRepository.save(person);

        personRepository.deleteById(person.getId());

        Person deletedPerson = personRepository.findById(person.getId()).orElse(null);

        Assertions.assertNull(deletedPerson);
    }

    @Test
    public void testGetAllPerson() {
        personRepository.save(person);

        Person person2 = new Person();
        person2.setFirstName("Name2");
        person2.setLastName("Last Name2");
        personRepository.save(person2);

        List<Person> personList = (List<Person>) personRepository.findAll();

        Assertions.assertEquals(2, personList.size());
    }


}
