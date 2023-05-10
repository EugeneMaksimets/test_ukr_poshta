package com.example.demo.converter;

import com.example.demo.entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonConverterTest {
    @Test
    void testPersonConverter() {
        Person personWithNewInfo = new Person();
        personWithNewInfo.setFirstName("Name");
        personWithNewInfo.setLastName("Last Name");

        Person personToUpdate = new Person();
        personToUpdate.setFirstName("Old Name");
        personToUpdate.setLastName("Old Last Name");

        Person result = PersonConverter.personConverter(personWithNewInfo, personToUpdate);

        assertNotNull(result);
        assertEquals(personToUpdate, result);
        assertEquals(personWithNewInfo.getFirstName(), result.getFirstName());
        assertEquals(personWithNewInfo.getLastName(), result.getLastName());
    }
}
