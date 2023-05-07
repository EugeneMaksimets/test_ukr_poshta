package com.example.demo.service;

import com.example.demo.entity.Person;

import java.util.List;

public interface PersonService {

    Person create(Person person);

    Person update(Person person);

    Person getById(Long id);

    void delete(Long id);

    List<Person> getAll();

    Person setLevel(Long id, String level);

}
