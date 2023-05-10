package com.example.demo.converter;

import com.example.demo.entity.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TeamConverterTest {
    @Test
    void testTeamConverter() {
        Team teamWithNewInfo = new Team();
        teamWithNewInfo.setName("New Name");

        Team teamToUpdate = new Team();
        teamToUpdate.setName("Old Name");

        Team result = TeamConverter.teamConverter(teamWithNewInfo, teamToUpdate);

        assertNotNull(result);
        assertEquals(teamToUpdate, result);
        assertEquals(teamWithNewInfo.getName(), result.getName());
    }
}
