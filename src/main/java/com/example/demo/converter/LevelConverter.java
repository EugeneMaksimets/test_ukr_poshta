package com.example.demo.converter;

import com.example.demo.entity.Level;

public class LevelConverter {
    public static Level levelConverter(Level levelWithNewInfo, Level levelToUpdate) {
        levelToUpdate.setName(levelWithNewInfo.getName());
        return levelToUpdate;
    }
}
