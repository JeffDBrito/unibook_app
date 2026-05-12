package com.unibook.app.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.model.Person;
import com.unibook.app.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public PersonResponse createPerson(String name, String email) {
        var person = new com.unibook.app.model.Person();
        person.setName(name);
        person.setEmail(email);
        return toResponse(personRepository.save(person));
    }

    public Person createPersonEntity(String name, String email) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);

        return personRepository.save(person);
    }

    public PersonResponse findById(Long id) {
        return toResponse(personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id)));
    }

    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream()
                .map(this::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public java.util.Optional<PersonResponse> findByName(String name) {
        return personRepository.findByName(name).map(this::toResponse);
    }

    public void deleteById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        
        personRepository.delete(person);
    }

    private PersonResponse toResponse(Person person) {
        PersonResponse response = new PersonResponse();
        response.setName(person.getName());
        response.setEmail(person.getEmail());
        return response;
    }
    
}
