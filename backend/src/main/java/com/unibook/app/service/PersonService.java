package com.unibook.app.service;


import org.springframework.stereotype.Service;

import com.unibook.app.model.Person;
import com.unibook.app.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person createPerson(String name) {
        var person = new com.unibook.app.model.Person();
        person.setName(name);
        return personRepository.save(person);
    }

    public java.util.Optional<Person> findByName(String name) {
        return personRepository.findByName(name);
    }
    
}
