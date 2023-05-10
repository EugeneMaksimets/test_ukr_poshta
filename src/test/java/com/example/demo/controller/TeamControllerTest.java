package com.example.demo.controller;

import com.example.demo.converter.TeamConverter;
import com.example.demo.entity.Team;
import com.example.demo.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private Team team;
    private Team team2;
    private List<Team> teams;

    @BeforeEach
    public void setUp() {

        team = new Team();
        team.setId(1L);
        team.setName("Name");

        team2 = new Team();
        team2.setId(2L);
        team2.setName("Name2");

        teams = new ArrayList<>();

    }

    @Test
    void createTest() {
        when(teamService.create(team)).thenReturn(team);

        Team createdTeam = teamController.create(team);

        verify(teamService).create(team);
        verify(teamService, times(1)).create(team);
        assertEquals(team, createdTeam);
    }

    @Test
    public void updateTest() {
        Team updatedProject = TeamConverter.teamConverter(team2, team);

        teamController.update(updatedProject);

        assertEquals(team2.getName(), updatedProject.getName());
        assertEquals(team.getId(), updatedProject.getId());
        assertNotEquals(team2.getId(), team.getId());
    }

    @Test
    public void getByIdTest() {
        when(teamService.getById(team.getId())).thenReturn(team);

        Team foundTeam = teamController.getById(1L);

        verify(teamService, times(1)).getById(any());
        verify(teamService).getById(team.getId());
        assertEquals(team, foundTeam);
    }

    @Test
    public void deleteTest() {
        teamController.delete(team.getId());

        verify(teamService, times(1)).delete(any());
        verify(teamService).delete(team.getId());
    }

    @Test
    public void getAllTest() {
        when(teamService.getAll()).thenReturn(teams);

        List<Team> result = teamController.getAll();

        assertEquals(teams, result);
        verify(teamService, times(1)).getAll();
    }

}
