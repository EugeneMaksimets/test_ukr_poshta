package com.example.demo.service.impl;

import com.example.demo.converter.PersonConverter;
import com.example.demo.entity.Person;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.PersonService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    RoleRepository roleRepository;

    @SneakyThrows
    @Override
    public Person create(Person person) {
        try {
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Person update(Person person) {
        try {
            Person peronForUpdate = personRepository.findById(person.getId()).orElseGet(Person::new);
            return personRepository.save(PersonConverter.personConverter(person, peronForUpdate));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Person getById(Long id) {
        return personRepository.findById(id).orElseThrow(
                () -> new BusinessException("Person with id " + id + " not found", null, HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            personRepository.deleteById(id);
            return ResponseEntity.ok("Person with id: ".concat(id.toString()).concat(" has been remove"));
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Cannot find ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Person> getAll() {
        try {
            return (List<Person>) personRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Error", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
