package com.example.demo.controller;

import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LevelController {

    @Autowired
    private LevelService levelService;

    @PostMapping("/level/create")
    public Level create(@RequestBody Level level) {
        return levelService.create(level);
    }

    @PutMapping("/level/update")
    public Level update(@RequestBody Level level) {
        return levelService.update(level);
    }

    @PutMapping("/level/set/person")
    public Person setLevel(@RequestParam Long id, @RequestParam String level) {
        return levelService.setLevel(id, level);
    }

    @DeleteMapping("/level/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return levelService.delete(id);
    }

    @GetMapping("/level")
    public Level getById(@RequestParam Long id) {
        return levelService.getById(id);
    }

    @GetMapping("/level/all")
    public List<Level> getAll() {
        return levelService.getAll();
    }

    @GetMapping("/level/all/name")
    public List<Person> getPersonsByLevel(@RequestParam String level) {
        return levelService.getByLevel(level);
    }

}
