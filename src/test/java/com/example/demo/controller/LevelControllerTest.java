package com.example.demo.controller;

import com.example.demo.converter.LevelConverter;
import com.example.demo.entity.Level;
import com.example.demo.entity.Person;
import com.example.demo.service.LevelService;
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
public class LevelControllerTest {

    @Mock
    private LevelService levelService;

    @InjectMocks
    private LevelController levelController;

    private Level level;
    private Level level2;
    private List<Level> levels;

    @BeforeEach
    public void setUp() {

        List<Person> personList = new ArrayList<>();

        level = new Level();
        level.setId(1L);
        level.setName("Level");
        level.setPersons(personList);

        level2 = new Level();
        level2.setId(2L);
        level2.setName("Level2");
        level2.setPersons(personList);

        levels = new ArrayList<>();

    }

    @Test
    void createTest() {
        when(levelService.create(level)).thenReturn(level);

        Level createdLevel = levelController.create(level);

        verify(levelService).create(level);
        verify(levelService, times(1)).create(level);
        assertEquals(level, createdLevel);
    }

    @Test
    public void updateTest() {
        Level updatedLevel = LevelConverter.levelConverter(level2, level);

        levelController.update(updatedLevel);

        assertEquals(level2.getName(), updatedLevel.getName());
        assertEquals(level.getId(), updatedLevel.getId());
        assertNotEquals(level2.getId(), level.getId());
    }

    @Test
    public void getByIdTest() {
        when(levelService.getById(level.getId())).thenReturn(level);

        Level foundLevel = levelController.getById(1L);

        verify(levelService, times(1)).getById(any());
        verify(levelService).getById(level.getId());
        assertEquals(level, foundLevel);
    }

    @Test
    public void deleteTest() {
        levelController.delete(level.getId());

        verify(levelService, times(1)).delete(any());
        verify(levelService).delete(level.getId());
    }

    @Test
    public void getAllTest() {
        when(levelService.getAll()).thenReturn(levels);

        List<Level> result = levelController.getAll();

        assertEquals(levels, result);
        verify(levelService, times(1)).getAll();
    }


}
