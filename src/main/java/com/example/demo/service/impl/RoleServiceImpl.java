package com.example.demo.service.impl;

import com.example.demo.converter.RoleConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Role;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    @SneakyThrows
    @Override
    public Role create(Role role) {
        try {
            return roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Role update(Role role) {
        try {
            Role roleForUpdate = roleRepository.findById(role.getId()).orElseGet(Role::new);
            return roleRepository.save(RoleConverter.roleConverter(role, roleForUpdate));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BusinessException("Can't parse JSON", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new BusinessException("Role with id " + id + " not found", null, HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            roleRepository.deleteById(id);
            return ResponseEntity.ok("Role with id: ".concat(id.toString()).concat(" has been remove"));
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Cannot find ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Role> getAll() {
        try {
            return (List<Role>) roleRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Error", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public Person setRole(Long id, String role) {
        try {
            Person person = personRepository.findById(id).orElseGet(Person::new);
            List<Role> roles = (List<Role>) roleRepository.findAll();
            for (Role tmp : roles) {
                List<Person> personList = tmp.getPersons();
                personList.remove(person);
                tmp.setPersons(personList);
                roleRepository.save(tmp);
                if (tmp.getName().equalsIgnoreCase(role)) {
                    person.setRole(tmp);
                    personList = tmp.getPersons();
                    personList.add(person);
                    tmp.setPersons(personList);
                    roleRepository.save(tmp);
                } else {
                    throw new BusinessException("Incorrect role", null, HttpStatus.NOT_FOUND);
                }
            }
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Incorrect ID", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public List<Person> getByRole(String roleString) {
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        Long roleId = null;
        for (Role tmp : roleList) {
            if (tmp.getName().equalsIgnoreCase(roleString)) {
                roleId = tmp.getId();
            }
        }
        try {
            assert roleId != null;
            Role role = roleRepository.findById(roleId).orElseGet(Role::new);
            return role.getPersons();
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BusinessException("Incorrect role in api string", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
