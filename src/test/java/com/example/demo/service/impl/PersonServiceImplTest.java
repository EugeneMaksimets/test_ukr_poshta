package com.example.demo.service.impl;

import com.example.demo.converter.PersonConverter;
import com.example.demo.entity.Person;
import com.example.demo.exception.BusinessException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;
    private Person person2;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setId(1L);
        person.setFirstName("Name");
        person.setLastName("Last Name");

        person2 = new Person();
        person2.setFirstName("Name2");
        person2.setLastName("Last Name2");
    }

    @Test
    public void createTest() {
        when(personRepository.save(person)).thenReturn(person);

        Person createdPerson = personService.create(person);

        assertEquals(person, createdPerson);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void createTestThrowException() {
        when(personRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> personService.create(person),
                "Expected create() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void updateTest() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        Person updatePerson = PersonConverter.personConverter(person2, person);

        personService.update(updatePerson);

        verify(personRepository, times(1)).findById(any());
        verify(personRepository, times(1)).save(any());
        assertEquals(person2.getFirstName(), updatePerson.getFirstName());
        assertEquals(person2.getLastName(), updatePerson.getLastName());
        assertEquals(person.getId(), updatePerson.getId());
    }

    @Test
    public void updateTestThrowException() {
        when(personRepository.findById(any())).thenThrow(InvalidDataAccessApiUsageException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> personService.update(person),
                "Expected update() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void getByIdTest() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        Person foundPerson = personService.getById(1L);

        verify(personRepository, times(1)).findById(person.getId());
        verify(personRepository).findById(person.getId());
        assertEquals(person, foundPerson);
    }

    @Test
    public void deleteTest() {
        personService.delete(person.getId());

        verify(personRepository, times(1)).deleteById(person.getId());
        verify(personRepository).deleteById(person.getId());
    }

    @Test
    public void deleteTest_LevelNotFound() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(id);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> personService.delete(id),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Cannot find ID", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());

    }

    @Test
    public void getAllTest() {
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person2);

        when(personRepository.findAll()).thenReturn(personList);

        List<Person> result = personService.getAll();

        assertEquals(personList, result);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest_Exception() {
        when(personRepository.findAll()).thenThrow(RuntimeException.class);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> personService.getAll(),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Error", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }
}
