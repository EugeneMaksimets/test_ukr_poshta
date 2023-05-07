package com.example.demo.service.impl;

import com.example.demo.converter.LevelConverter;
import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    PersonRepository personRepository;

    @Override
    public Level create(Level level) {
        return levelRepository.save(level);
    }

    @Override
    public Level update(Level level) {
        Level levelForUpdate = levelRepository.findById(level.getId()).orElseGet(Level::new);
        return levelRepository.save(LevelConverter.levelConverter(level, levelForUpdate));
    }

    @Override
    public Level getById(Long id) {
        Optional<Level> level = levelRepository.findById(id);
        return level.orElseGet(Level::new);
    }

    @Override
    public void delete(Long id) {
        levelRepository.deleteById(id);
    }

    @Override
    public List<Level> getAll() {
        return (List<Level>) levelRepository.findAll();
    }

}
