package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/person/create")
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping("/person/update")
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping("/person/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return personService.delete(id);
    }

    @GetMapping("/person")
    public Person getById(@RequestParam Long id) {
        return personService.getById(id);
    }

    @GetMapping("/person/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

}