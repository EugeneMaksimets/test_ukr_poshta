package com.example.demo.service.impl;

import com.example.demo.converter.TeamConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Team;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Team create(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        Team teamForUpdate = teamRepository.findById(team.getId()).orElseGet(Team::new);
        return teamRepository.save(TeamConverter.teamConverter(team, teamForUpdate));
    }

    @Override
    public Team getById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.orElseGet(Team::new);
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public List<Team> getAll() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public Team addPersonById(Long idTeam, Long idPerson) {
        Person person = personRepository.findById(idPerson).orElseGet(Person::new);
        Team team = teamRepository.findById(idTeam).orElseGet(Team::new);
        List<Person> personListFromTeam = team.getPersons();
        personListFromTeam.add(person);
        team.setPersons(personListFromTeam);
        return teamRepository.save(team);
    }

    @Override
    public Team addPerson(Long id, Person person) {
        personRepository.save(person);
        Team team = teamRepository.findById(id).orElseGet(Team::new);
        List<Person> personList = team.getPersons();
        personList.add(person);
        team.setPersons(personList);
        return teamRepository.save(team);
    }

    @Override
    public Team deletePerson(Long idTeam, Long idPerson) {
        Person person = personRepository.findById(idPerson).orElseGet(Person::new);
        Team team = teamRepository.findById(idTeam).orElseGet(Team::new);
        List<Person> personListFromTeam = team.getPersons();
        personListFromTeam.remove(person);
        team.setPersons(personListFromTeam);
        return teamRepository.save(team);
    }
}
