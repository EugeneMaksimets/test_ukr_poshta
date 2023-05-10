package com.example.demo.service.impl;

import com.example.demo.converter.LevelConverter;
import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.LevelRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.LevelService;
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
public class LevelServiceImpl implements LevelService {

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    PersonRepository personRepository;

    @SneakyThrows
    @Override
    public Level create(Level level) {
        try {
            return levelRepository.save(level);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Level update(Level level) {
        try {
            Level levelForUpdate = levelRepository.findById(level.getId()).orElseGet(Level::new);
            return levelRepository.save(LevelConverter.levelConverter(level, levelForUpdate));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Level getById(Long id) {
        return levelRepository.findById(id).orElseThrow(
                () -> new BusinessException("Level with id " + id + " not found", null, HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            levelRepository.deleteById(id);
            return ResponseEntity.ok("Level with id: ".concat(id.toString()).concat(" has been remove"));
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Cannot find ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Level> getAll() {
        try {
            return (List<Level>) levelRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Error", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Person setLevel(Long id, String level) {
        try {
            Person person = personRepository.findById(id).orElseGet(Person::new);
            List<Level> levels = (List<Level>) levelRepository.findAll();
            for (Level tmp : levels) {
                List<Person> personList = tmp.getPersons();
                personList.remove(person);
                tmp.setPersons(personList);
                levelRepository.save(tmp);
                if (tmp.getName().equalsIgnoreCase(level)) {
                    person.setLevel(tmp);
                    personList = tmp.getPersons();
                    personList.add(person);
                    tmp.setPersons(personList);
                    levelRepository.save(tmp);
                    break;
                } else {
                    throw new BusinessException("Incorrect level", null, HttpStatus.NOT_FOUND);
                }
            }
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Cannot find ID: ".concat(id.toString()), e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Person> getByLevel(String levelString) {
        List<Level> levelList = (List<Level>) levelRepository.findAll();
        Long levelId = null;
        for (Level tmp : levelList) {
            if (tmp.getName().equalsIgnoreCase(levelString)) {
                levelId = tmp.getId();
            }
        }
        try {
            assert levelId != null;
            Level level = levelRepository.findById(levelId).orElseGet(Level::new);
            return level.getPersons();
        } catch (InvalidDataAccessApiUsageException | NullPointerException e) {
            throw new BusinessException("Incorrect level in api string", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
