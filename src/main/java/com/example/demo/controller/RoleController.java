package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.entity.Role;
import com.example.demo.service.PersonService;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role/create")
    public Role create(@RequestBody Role role) {
        return roleService.create(role);
    }

    @PutMapping("/role/update")
    public Role update(@RequestBody Role role) {
        return roleService.update(role);
    }

    @DeleteMapping("/role/delete/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    @GetMapping("/role/{id}")
    public Role getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @GetMapping("/role/all")
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @PutMapping("/person/{id}/set/role/{role}")
    public Person setRole(@PathVariable Long id, @PathVariable String role) {
        return roleService.setRole(id, role);
    }
}
