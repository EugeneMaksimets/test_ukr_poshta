package com.example.demo.service.impl;

import com.example.demo.converter.RoleConverter;
import com.example.demo.entity.Role;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.RoleRepository;
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
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private Role role2;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("Name");

        role2 = new Role();
        role2.setId(2L);
        role2.setName("Name2");
    }

    @Test
    public void createTest() {
        when(roleRepository.save(role)).thenReturn(role);

        Role createdRole = roleService.create(role);

        assertEquals(role, createdRole);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void createTestThrowException() {
        when(roleRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> roleService.create(role),
                "Expected create() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void updateTest() {
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);

        Role updateRole = RoleConverter.roleConverter(role2, role);

        roleService.update(updateRole);

        verify(roleRepository, times(1)).findById(any());
        verify(roleRepository, times(1)).save(any());
        assertEquals(role2.getName(), updateRole.getName());
        assertEquals(role.getId(), updateRole.getId());
    }

    @Test
    public void updateTestThrowException() {
        when(roleRepository.findById(any())).thenThrow(InvalidDataAccessApiUsageException.class);

        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> roleService.update(role),
                "Expected update() to throw, but it didn't"
        );

        assertEquals("Can't parse JSON", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    public void getByIdTest() {
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        Role foundRole = roleService.getById(1L);

        verify(roleRepository, times(1)).findById(role.getId());
        verify(roleRepository).findById(role.getId());
        assertEquals(role, foundRole);
    }

    @Test
    public void deleteTest() {
        roleService.delete(role.getId());

        verify(roleRepository, times(1)).deleteById(role.getId());
        verify(roleRepository).deleteById(role.getId());
    }

    @Test
    public void deleteTest_LevelNotFound() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(roleRepository).deleteById(id);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> roleService.delete(id),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Cannot find ID", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());

    }

    @Test
    public void getAllTest() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        roleList.add(role2);

        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> result = roleService.getAll();

        assertEquals(roleList, result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest_Exception() {
        when(roleRepository.findAll()).thenThrow(RuntimeException.class);
        BusinessException thrown = Assertions.assertThrows(
                BusinessException.class,
                () -> roleService.getAll(),
                "Expected delete() to throw, but it didn't"
        );
        assertEquals("Error", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }
}
