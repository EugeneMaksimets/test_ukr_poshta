package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LevelTest {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private PersonRepository personRepository;

    private Level level;
    private Person person1;
    private Person person2;

    private int inDataCount;

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

        level = new Level();
        level.setName("Level");
        level.setPersons(persons);

        List<Level> levels = (List<Level>) levelRepository.findAll();
        inDataCount = levels.size();
    }

    @Test
    public void testSaveLevel() {
        Level savedLevel = levelRepository.save(level);

        Assertions.assertNotNull(savedLevel.getId());
        Assertions.assertEquals("Level", savedLevel.getName());
        Assertions.assertEquals(2, savedLevel.getPersons().size());
    }

    @Test
    public void testGetProjectById() {
        levelRepository.save(level);

        Level retrievedLevel = levelRepository.findById(level.getId()).orElse(null);

        Assertions.assertNotNull(retrievedLevel);
        Assertions.assertEquals("Level", level.getName());
        Assertions.assertEquals(2, retrievedLevel.getPersons().size());
    }

    @Test
    public void testUpdateLevel() {
        levelRepository.save(level);

        level.setName("New Name");
        level.getPersons().remove(person2);

        Level updatedLevel = levelRepository.save(level);

        Assertions.assertEquals("New Name", updatedLevel.getName());
        Assertions.assertEquals(1, updatedLevel.getPersons().size());
        Assertions.assertTrue(updatedLevel.getPersons().contains(person1));
        Assertions.assertFalse(updatedLevel.getPersons().contains(person2));
    }

    @Test
    public void testDeleteLevel() {
        levelRepository.save(level);

        levelRepository.deleteById(level.getId());

        Level deletedLevel = levelRepository.findById(level.getId()).orElse(null);

        Assertions.assertNull(deletedLevel);
    }

    @Test
    public void testGetAllLevel() {
        levelRepository.save(level);

        Level level2 = new Level();
        level2.setName("Level 2");
        levelRepository.save(level2);

        List<Level> levelList = (List<Level>) levelRepository.findAll();

        Assertions.assertEquals(inDataCount + 2, levelList.size());
    }

}
