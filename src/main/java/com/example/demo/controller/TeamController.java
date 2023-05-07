package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.entity.Team;
import com.example.demo.service.TeamService;
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

    @PutMapping("team/{teamId}/add/person/{personId}")
    public Team addPersonById(@PathVariable Long teamId, @PathVariable Long personId) {
        return teamService.addPersonById(teamId, personId);
    }

    @PutMapping("team/{id}/add/person")
    public Team addPerson(@PathVariable Long id, @RequestBody Person person) {
        return teamService.addPerson(id, person);
    }

    @PutMapping("team/{teamId}/delete/person/{personId}")
    public Team removePerson(@PathVariable Long teamId, @PathVariable Long personId) {
        return teamService.deletePerson(teamId, personId);
    }

    @DeleteMapping("/team/delete/{id}")
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }

    @GetMapping("/team/{id}")
    public Team getById(@PathVariable Long id) {
        return teamService.getById(id);
    }

    @GetMapping("/team/all")
    public List<Team> getAll() {
        return teamService.getAll();
    }

}
