package com.example.demo.controller;

import com.example.demo.converter.RoleConverter;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
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
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role;
    private Role role2;
    private List<Role> projects;

    @BeforeEach
    public void setUp() {

        role = new Role();
        role.setId(1L);
        role.setName("Name");

        role2 = new Role();
        role2.setId(2L);
        role2.setName("Name2");

        projects = new ArrayList<>();

    }

    @Test
    void createTest() {
        when(roleService.create(role)).thenReturn(role);

        Role createdRole = roleController.create(role);

        verify(roleService).create(role);
        verify(roleService, times(1)).create(role);
        assertEquals(role, createdRole);
    }

    @Test
    public void updateTest() {
        Role updatedProject = RoleConverter.roleConverter(role2, role);

        roleController.update(updatedProject);

        assertEquals(role2.getName(), updatedProject.getName());
        assertEquals(role.getId(), updatedProject.getId());
        assertNotEquals(role2.getId(), role.getId());
    }

    @Test
    public void getByIdTest() {
        when(roleService.getById(role.getId())).thenReturn(role);

        Role foundRole = roleController.getById(1L);

        verify(roleService, times(1)).getById(any());
        verify(roleService).getById(role.getId());
        assertEquals(role, foundRole);
    }

    @Test
    public void deleteTest() {
        roleController.delete(role.getId());

        verify(roleService, times(1)).delete(any());
        verify(roleService).delete(role.getId());
    }

    @Test
    public void getAllTest() {
        when(roleService.getAll()).thenReturn(projects);

        List<Role> result = roleController.getAll();

        assertEquals(projects, result);
        verify(roleService, times(1)).getAll();
    }


}
