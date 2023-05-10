package com.example.demo.converter;

import com.example.demo.entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleConverterTest {
    @Test
    void testRoleConverter() {
        Role roleWithNewInfo = new Role();
        roleWithNewInfo.setName("New Name");

        Role roleToUpdate = new Role();
        roleToUpdate.setName("Old Name");

        Role result = RoleConverter.roleConverter(roleWithNewInfo, roleToUpdate);

        assertNotNull(result);
        assertEquals(roleToUpdate, result);
        assertEquals(roleWithNewInfo.getName(), result.getName());
    }
}
