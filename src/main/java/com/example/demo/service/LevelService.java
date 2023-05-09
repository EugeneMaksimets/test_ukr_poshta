package com.example.demo.service;

import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LevelService {
    Level create(Level level);

    Level update(Level level);

    Level getById(Long id);

    ResponseEntity<?> delete(Long id);

    List<Level> getAll();

    Person setLevel(Long id, String level);

    List<Person> getByLevel(String level);

}
