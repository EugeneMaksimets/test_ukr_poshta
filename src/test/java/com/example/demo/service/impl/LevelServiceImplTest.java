package com.example.demo.service.impl;

import com.example.demo.converter.LevelConverter;
import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LevelServiceImplTest {

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private LevelServiceImpl levelService;

    private Level level;
    private Level level2;
    private List<Level> levels;
    private Person person;
    private Person person2;

    @BeforeEach
    public void setUp() {
        List<Person> personList = new ArrayList<>();
        personList.add(person2);

        level = new Level();
        level.setId(1L);
        level.setName("Level");
        level.setPersons(personList);

        level2 = new Level();
        level2.setId(2L);
        level2.setName("Level2");

        person = new Person();
        person.setId(1L);
        person.setFirstName("Name");
        person.setLastName("Name");
        person.setLevel(level);

        person2 = new Person();
        person2.setId(2L);
        person2.setFirstName("Name2");
        person2.setLastName("Name2");
        person2.setLevel(level);

        levels = new ArrayList<>();
        levels.add(level);
        levels.add(level2);

    }

    @Test
    public void createTest() {
        when(levelRepository.save(level)).thenReturn(level);

        Level createdLevel = levelService.create(level);

        verify(levelRepository, times(1)).save(any());
        assertEquals(level, createdLevel);
    }

    @Test
    public void createTestThrowException() {
        when(levelRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> levelService.create(level),
                "Expected create() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void updateTest() {
        when(levelRepository.findById(level.getId())).thenReturn(Optional.of(level));
        when(levelRepository.save(level)).thenReturn(level);

        Level updatedLevel = LevelConverter.levelConverter(level2, level);

        levelService.update(updatedLevel);

        verify(levelRepository, times(1)).findById(any());
        verify(levelRepository, times(1)).save(any());
        assertEquals(level2.getName(), updatedLevel.getName());
        assertEquals(level.getId(), updatedLevel.getId());
    }

    @Test
    public void updateTestThrowException() {
        when(levelRepository.findById(any())).thenThrow(InvalidDataAccessApiUsageException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> levelService.update(level),
                "Expected update() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void getByIdTest() {
        when(levelRepository.findById(level.getId())).thenReturn(Optional.of(level));

        Level foundLevel = levelService.getById(1L);

        verify(levelRepository, times(1)).findById(any());
        verify(levelRepository).findById(level.getId());
        assertEquals(level, foundLevel);
    }

    @Test
    public void deleteTest() {
        levelService.delete(level.getId());

        verify(levelRepository, times(1)).deleteById(any());
        verify(levelRepository).deleteById(level.getId());
    }

    @Test
    public void deleteTest_LevelNotFound() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(levelRepository).deleteById(id);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> levelService.delete(id),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Cannot find ID", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());

    }

    @Test
    public void getAllTest() {
        when(levelRepository.findAll()).thenReturn(levels);

        List<Level> result = levelService.getAll();

        assertEquals(levels, result);
        verify(levelRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest_Exception() {
        when(levelRepository.findAll()).thenThrow(RuntimeException.class);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> levelService.getAll(),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Error", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    void testSetLevel() {
        Long id = 1L;
        String nameLevel = "level";
        person.setLevel(level2);
        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(levelRepository.findAll()).thenReturn(levels);
        when(levelRepository.save(level)).thenReturn(level);

        Person result = levelService.setLevel(id, nameLevel);

        assertEquals(level, result.getLevel());
        assertTrue(level.getPersons().contains(person));
        assertTrue(result.getLevel().getName().equalsIgnoreCase(nameLevel));
        verify(levelRepository, times(2)).save(any(Level.class));
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void setLevelTest_LevelNotFound() {
        String nameLevel = "Non Existent Level";

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(new Person()));
        when(levelRepository.findAll()).thenReturn(levels);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> levelService.setLevel(person.getId(), nameLevel),
                "Expected setLevel() to throw, but it didn't"
        );
        assertEquals("Incorrect level", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
    }

    @Test
    public void setLevelTest_IdNotFound() {
        Long id = 5L;
        String nameLevel = "level";

        when(personRepository.findById(id)).thenThrow(DataIntegrityViolationException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> levelService.setLevel(id, nameLevel),
                "Expected setLevel() to throw, but it didn't"
        );
        assertEquals("Cannot find ID: ".concat(id.toString()), thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
    }

    @Test
    void testGetByLevel() {
        String nameLevel = "Level";
        when(levelRepository.findAll()).thenReturn(levels);
        when(levelRepository.findById(level.getId())).thenReturn(Optional.of(level));

        List<Person> result = levelService.getByLevel(nameLevel);

        assertNotNull(result);
        assertEquals(person.getLevel().getName(), nameLevel);
        assertEquals(1, result.size());
        verify(levelRepository, times(1)).findAll();
        verify(levelRepository, times(1)).findById(any());
    }

}