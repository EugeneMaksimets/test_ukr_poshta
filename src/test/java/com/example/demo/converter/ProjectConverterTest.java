package com.example.demo.converter;

import com.example.demo.entity.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectConverterTest {
    @Test
    void testProjectConverter() {
        Project projectWithNewInfo = new Project();
        projectWithNewInfo.setName("Name");
        projectWithNewInfo.setDescription("description");

        Project projectToUpdate = new Project();
        projectToUpdate.setName("Old Name");
        projectToUpdate.setDescription("Old description");

        Project result = ProjectConverter.projectConvertor(projectWithNewInfo, projectToUpdate);

        assertNotNull(result);
        assertEquals(projectToUpdate, result);
        assertEquals(projectWithNewInfo.getName(), result.getName());
        assertEquals(projectWithNewInfo.getDescription(), result.getDescription());
    }
}
