package com.example.demo.service.impl;

import com.example.demo.converter.PersonConverter;
import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.entity.Role;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        Person peronForUpdate = personRepository.findById(person.getId()).orElseGet(Person::new);
        return personRepository.save(PersonConverter.personConverter(person, peronForUpdate));
    }

    @Override
    public Person getById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseGet(Person::new);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getAll() {
        return (List<Person>) personRepository.findAll();
    }


}
