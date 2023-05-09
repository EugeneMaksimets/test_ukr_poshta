package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/role/set/person")
    public Person setRole(@RequestParam Long id, @RequestParam String roleId) {
        return roleService.setRole(id, roleId);
    }

    @DeleteMapping("/role/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return roleService.delete(id);
    }

    @GetMapping("/role")
    public Role getById(@RequestParam Long id) {
        return roleService.getById(id);
    }

    @GetMapping("/role/all")
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/role/name")
    public List<Person> getPersonsByRole(@RequestParam String role) {
        return roleService.getByRole(role);
    }
}
