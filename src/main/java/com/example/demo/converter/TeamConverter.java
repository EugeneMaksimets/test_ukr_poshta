package com.example.demo.converter;

import com.example.demo.entity.Team;

public class TeamConverter {
    public static Team teamConverter(Team teamWithNewInfo, Team teamToUpdate) {
        teamToUpdate.setName(teamWithNewInfo.getName());
        return teamToUpdate;
    }
}
