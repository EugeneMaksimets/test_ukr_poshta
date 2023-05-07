package com.example.demo.converter;

import com.example.demo.entity.Person;

public class PersonConverter {
    public static Person personConverter(Person personWithNewInfo, Person peronToUpdate) {
        peronToUpdate.setFirstName(personWithNewInfo.getFirstName());
        peronToUpdate.setLastName(personWithNewInfo.getLastName());
        return peronToUpdate;
    }
}
