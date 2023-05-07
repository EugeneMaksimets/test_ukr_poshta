package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.entity.Role;

import java.util.List;

public interface RoleService {

    Role create(Role role);

    Role update(Role role);

    Role getById(Long id);

    void delete(Long id);

    List<Role> getAll();

    Person setRole(Long id, String role);

    List<Person> getByRole(String role);

}
