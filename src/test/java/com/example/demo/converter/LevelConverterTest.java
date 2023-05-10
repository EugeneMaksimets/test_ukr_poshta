package com.example.demo.converter;

import com.example.demo.entity.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LevelConverterTest {

    @Test
    void testLevelConverter() {
        Level levelWithNewInfo = new Level();
        levelWithNewInfo.setName("New Level Name");

        Level levelToUpdate = new Level();
        levelToUpdate.setName("Old Level Name");

        Level result = LevelConverter.levelConverter(levelWithNewInfo, levelToUpdate);

        assertNotNull(result);
        assertEquals(levelToUpdate, result);
        assertEquals(levelWithNewInfo.getName(), result.getName());
    }
}
