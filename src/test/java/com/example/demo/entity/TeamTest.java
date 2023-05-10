package com.example.demo.entity;

import com.example.demo.entity.Person;
import com.example.demo.entity.Team;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonRepository personRepository;

    private Team team;
    private Person person1;
    private Person person2;

    @BeforeEach
    public void setUp() {
        person1 = new Person();
        person1.setFirstName("Name");
        person1.setLastName("Last Name");
        person1 = personRepository.save(person1);

        person2 = new Person();
        person2.setFirstName("Name1");
        person2.setLastName("Last Name2");
        person2 = personRepository.save(person2);

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

        team = new Team();
        team.setName("Team");
        team.setPersons(persons);
    }

    @Test
    public void testSaveTeam() {
        Team savedTeam = teamRepository.save(team);

        Assertions.assertNotNull(savedTeam.getId());
        Assertions.assertEquals("Team", savedTeam.getName());
        Assertions.assertEquals(2, savedTeam.getPersons().size());
    }

    @Test
    public void testGetProjectById() {
        teamRepository.save(team);

        Team retrievedTeam = teamRepository.findById(team.getId()).orElse(null);

        Assertions.assertNotNull(retrievedTeam);
        Assertions.assertEquals("Team", retrievedTeam.getName());
        Assertions.assertEquals(2, retrievedTeam.getPersons().size());
    }

    @Test
    public void testUpdateTeam() {
        teamRepository.save(team);

        team.setName("New Name");
        team.getPersons().remove(person2);

        Team updatedTeam = teamRepository.save(team);

        Assertions.assertEquals("New Name", updatedTeam.getName());
        Assertions.assertEquals(1, updatedTeam.getPersons().size());
        Assertions.assertTrue(updatedTeam.getPersons().contains(person1));
        Assertions.assertFalse(updatedTeam.getPersons().contains(person2));
    }

    @Test
    public void testDeleteTeam() {
        teamRepository.save(team);

        teamRepository.deleteById(team.getId());

        Team deletedTeam = teamRepository.findById(team.getId()).orElse(null);

        Assertions.assertNull(deletedTeam);
    }

    @Test
    public void testGetAllTeam() {
        teamRepository.save(team);

        Team team2 = new Team();
        team2.setName("Team 2");
        teamRepository.save(team2);

        List<Team> projects = (List<Team>) teamRepository.findAll();

        Assertions.assertEquals(2, projects.size());
    }
}
