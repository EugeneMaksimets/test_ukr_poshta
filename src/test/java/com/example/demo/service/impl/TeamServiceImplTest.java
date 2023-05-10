package com.example.demo.service.impl;

import com.example.demo.converter.TeamConverter;
import com.example.demo.entity.Team;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team team;
    private Team team2;

    @BeforeEach
    public void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Name");

        team2 = new Team();
        team2.setId(2L);
        team2.setName("Name2");
    }

    @Test
    public void createTest() {
        when(teamRepository.save(team)).thenReturn(team);

        Team createdTeam = teamService.create(team);

        assertEquals(team, createdTeam);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    public void createTestThrowException() {
        when(teamRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> teamService.create(team),
                "Expected create() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void updateTest() {
        when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));
        when(teamRepository.save(team)).thenReturn(team);

        Team updateTeam = TeamConverter.teamConverter(team2, team);

        teamService.update(updateTeam);

        verify(teamRepository, times(1)).findById(any());
        verify(teamRepository, times(1)).save(any());
        assertEquals(team2.getName(), updateTeam.getName());
        assertEquals(team.getId(), updateTeam.getId());
    }

    @Test
    public void updateTestThrowException() {
        when(teamRepository.findById(any())).thenThrow(InvalidDataAccessApiUsageException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> teamService.update(team),
                "Expected update() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void getByIdTest() {
        when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

        Team foundTeam = teamService.getById(1L);

        verify(teamRepository, times(1)).findById(team.getId());
        verify(teamRepository).findById(team.getId());
        assertEquals(team, foundTeam);
    }

    @Test
    public void deleteTest() {
        teamService.delete(team.getId());

        verify(teamRepository, times(1)).deleteById(team.getId());
        verify(teamRepository).deleteById(team.getId());
    }

    @Test
    public void deleteTest_LevelNotFound() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(teamRepository).deleteById(id);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> teamService.delete(id),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Cannot find ID", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());

    }

    @Test
    public void getAllTest() {
        List<Team> teamList = new ArrayList<>();
        teamList.add(team);
        teamList.add(team2);

        when(teamRepository.findAll()).thenReturn(teamList);

        List<Team> result = teamService.getAll();

        assertEquals(teamList, result);
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest_Exception() {
        when(teamRepository.findAll()).thenThrow(RuntimeException.class);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> teamService.getAll(),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Error", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }
}
