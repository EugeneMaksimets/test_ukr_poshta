package com.example.demo.service.impl;

import com.example.demo.converter.TeamConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Team;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonRepository personRepository;

    @SneakyThrows
    @Override
    public Team create(Team team) {
        try {
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Team update(Team team) {
        try {
            Team teamForUpdate = teamRepository.findById(team.getId()).orElseGet(Team::new);
            return teamRepository.save(TeamConverter.teamConverter(team, teamForUpdate));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Team getById(Long id) {
        return teamRepository.findById(id).orElseThrow(
                () -> new BusinessException("Team with id " + id + " not found", null, HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            teamRepository.deleteById(id);
            return ResponseEntity.ok("Team with id: ".concat(id.toString()).concat(" has been remove"));
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Cannot find ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Team> getAll() {
        try {
            return (List<Team>) teamRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Error", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Team addPersonById(Long idTeam, Long idPerson) {
        try {
            Person person = personRepository.findById(idPerson).orElseGet(Person::new);
            Team team = teamRepository.findById(idTeam).orElseGet(Team::new);
            person.setTeam(team);
            personRepository.save(person);
            List<Person> personListFromTeam = team.getPersons();
            personListFromTeam.add(person);
            team.setPersons(personListFromTeam);
            return teamRepository.save(team);
        } catch (InvalidDataAccessApiUsageException | DataIntegrityViolationException e) {
            throw new BusinessException("Invalid ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public Team addPerson(Long id, Person person) {
        Team team;
        team = teamRepository.findById(id).orElseGet(Team::new);
        if (team.getId() == null) {
            throw new BusinessException("Invalid ID", null, HttpStatus.NOT_FOUND);
        }
        try {
            person.setTeam(team);
            personRepository.save(person);
            List<Person> personList = team.getPersons();
            personList.add(person);
            team.setPersons(personList);
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Invalid person JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Team deletePerson(Long idTeam, Long idPerson) {
        Person person;
        person = personRepository.findById(idPerson).orElseGet(Person::new);
        if (person.getId() == null) {
            throw new BusinessException("Invalid person ID", null, HttpStatus.NOT_FOUND);
        }
        try {
            Team team = teamRepository.findById(idTeam).orElseGet(Team::new);
            List<Person> personListFromTeam = team.getPersons();
            personListFromTeam.remove(person);
            team.setPersons(personListFromTeam);
            return teamRepository.save(team);
        } catch (NullPointerException e) {
            throw new BusinessException("Invalid team ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}