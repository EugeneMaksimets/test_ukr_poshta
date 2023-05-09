package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.entity.Team;
import com.example.demo.service.TeamService;
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
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/team/create")
    public Team create(@RequestBody Team team) {
        return teamService.create(team);
    }

    @PutMapping("/team/update")
    public Team update(@RequestBody Team team) {
        return teamService.update(team);
    }

    @PutMapping("team/add/person")
    public Team addPersonById(@RequestParam Long id, @RequestParam Long teamId) {
        return teamService.addPersonById(teamId, id);
    }

    @PutMapping("team/add/person/team")
    public Team addPerson(@RequestParam Long id, @RequestBody Person person) {
        return teamService.addPerson(id, person);
    }

    @PutMapping("team/delete/person")
    public Team removePerson(@RequestParam Long id, @RequestParam Long teamId) {
        return teamService.deletePerson(teamId, id);
    }

    @DeleteMapping("/team/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return teamService.delete(id);
    }

    @GetMapping("/team")
    public Team getById(@RequestParam Long id) {
        return teamService.getById(id);
    }

    @GetMapping("/team/all")
    public List<Team> getAll() {
        return teamService.getAll();
    }

}
