package com.example.demo.controller;

import com.example.demo.converter.PersonConverter;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
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
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private Person person;
    private Person person2;
    private List<Person> persons;

    @BeforeEach
    public void setUp() {

        person = new Person();
        person.setId(1L);
        person.setFirstName("Name");
        person.setLastName("Last Name");

        person2 = new Person();
        person2.setId(2L);
        person2.setFirstName("Name2");
        person2.setLastName("Name Last2");

        persons = new ArrayList<>();

    }

    @Test
    void createTest() {
        when(personService.create(person)).thenReturn(person);

        Person createdPerson = personController.create(person);

        verify(personService).create(person);
        verify(personService, times(1)).create(person);
        assertEquals(person, createdPerson);
    }

    @Test
    public void updateTest() {
        Person updatedPerson = PersonConverter.personConverter(person2, person);

        personController.update(updatedPerson);

        assertEquals(person2.getFirstName(), updatedPerson.getFirstName());
        assertEquals(person2.getLastName(), updatedPerson.getLastName());
        assertEquals(person.getId(), updatedPerson.getId());
        assertNotEquals(person2.getId(), person.getId());
    }

    @Test
    public void getByIdTest() {
        when(personService.getById(person.getId())).thenReturn(person);

        Person foundPerson = personController.getById(1L);

        verify(personService, times(1)).getById(any());
        verify(personService).getById(person.getId());
        assertEquals(person, foundPerson);
    }

    @Test
    public void deleteTest() {
        personController.delete(person.getId());

        verify(personService, times(1)).delete(any());
        verify(personService).delete(person.getId());
    }

    @Test
    public void getAllTest() {
        when(personService.getAll()).thenReturn(persons);

        List<Person> result = personController.getAll();

        assertEquals(persons, result);
        verify(personService, times(1)).getAll();
    }


}
