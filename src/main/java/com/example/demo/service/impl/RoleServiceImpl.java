package com.example.demo.service.impl;

import com.example.demo.converter.RoleConverter;
import com.example.demo.entity.Person;
import com.example.demo.entity.Role;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        Role roleForUpdate = roleRepository.findById(role.getId()).orElseGet(Role::new);
        return roleRepository.save(RoleConverter.roleConverter(role, roleForUpdate));
    }

    @Override
    public Role getById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElseGet(Role::new);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Person setRole(Long id, String role) {
        Person person = personRepository.findById(id).orElseGet(Person::new);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        for (Role tmp : roles) {
            List<Person> personList = tmp.getPersons();
            personList.remove(person);
            tmp.setPersons(personList);
            roleRepository.save(tmp);
            if(tmp.getName().equalsIgnoreCase(role)) {
                person.setRole(tmp);
                personList = tmp.getPersons();
                personList.add(person);
                tmp.setPersons(personList);
                roleRepository.save(tmp);
            }
        }
        return personRepository.save(person);
    }
}
