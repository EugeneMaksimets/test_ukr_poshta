package com.example.demo.service;

import com.example.demo.entity.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {

    Person create(Person person);

    Person update(Person person);

    Person getById(Long id);

    ResponseEntity<?> delete(Long id);

    List<Person> getAll();

}
