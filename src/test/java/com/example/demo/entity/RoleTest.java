package com.example.demo.entity;

import com.example.demo.entity.Person;
import com.example.demo.entity.Role;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    private Role role;
    private Person person1;
    private Person person2;

    private int inDataCount;

    @BeforeEach
    public void setUp() {
        person1 = new Person();
        person1.setFirstName("Name");
        person1.setLastName("Last Name");
        person1 = personRepository.save(person1);

        person2 = new Person();
        person2.setFirstName("Name1");
        person2.setLastName("Last Name2");
        person2 = personRepository.save(person2);

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

        role = new Role();
        role.setName("Role");
        role.setPersons(persons);

        List<Role> roles = (List<Role>) roleRepository.findAll();
        inDataCount = roles.size();
    }

    @Test
    public void testSaveRole() {
        Role savedRole = roleRepository.save(role);

        Assertions.assertNotNull(savedRole.getId());
        Assertions.assertEquals("Role", savedRole.getName());
        Assertions.assertEquals(2, savedRole.getPersons().size());
    }

    @Test
    public void testGetRoleById() {
        roleRepository.save(role);

        Role retrievedRole = roleRepository.findById(role.getId()).orElse(null);

        Assertions.assertNotNull(retrievedRole);
        Assertions.assertEquals("Role", role.getName());
        Assertions.assertEquals(2, retrievedRole.getPersons().size());
    }

    @Test
    public void testUpdateRole() {
        roleRepository.save(role);

        role.setName("New Name");
        role.getPersons().remove(person2);

        Role updatedRole = roleRepository.save(role);

        Assertions.assertEquals("New Name", updatedRole.getName());
        Assertions.assertEquals(1, updatedRole.getPersons().size());
        Assertions.assertTrue(updatedRole.getPersons().contains(person1));
        Assertions.assertFalse(updatedRole.getPersons().contains(person2));
    }

    @Test
    public void testDeleteRole() {
        roleRepository.save(role);

        roleRepository.deleteById(role.getId());

        Role deletedRole = roleRepository.findById(role.getId()).orElse(null);

        Assertions.assertNull(deletedRole);
    }

    @Test
    public void testGetAllRoles() {
        roleRepository.save(role);

        Role role2 = new Role();
        role2.setName("Role 2");
        roleRepository.save(role2);

        List<Role> roleList = (List<Role>) roleRepository.findAll();

        Assertions.assertEquals(inDataCount + 2, roleList.size());
    }
}
