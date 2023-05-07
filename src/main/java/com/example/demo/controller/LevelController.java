package com.example.demo.controller;

import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LevelController {
    @Autowired
    LevelService levelService;

    @PostMapping("/level/create")
    public Level create(@RequestBody Level level) {
        return levelService.create(level);
    }

    @PutMapping("/level/update")
    public Level update(@RequestBody Level level) {
        return levelService.update(level);
    }

    @DeleteMapping("/level/delete/{id}")
    public void delete(@PathVariable Long id) {
        levelService.delete(id);
    }

    @GetMapping("/level/{id}")
    public Level getById(@PathVariable Long id) {
        return levelService.getById(id);
    }

    @GetMapping("/level/all")
    public List<Level> getAll() {
        return levelService.getAll();
    }

}
