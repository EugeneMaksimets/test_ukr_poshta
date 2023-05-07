package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.entity.Team;

import java.util.List;

public interface TeamService {

    Team create(Team team);

    Team update(Team team);

    Team getById(Long id);

    void delete(Long id);

    List<Team> getAll();

    Team addPersonById(Long idTeam, Long idPerson);

    Team addPerson(Long id, Person person);

    Team deletePerson(Long idTeam, Long idPerson);

}
