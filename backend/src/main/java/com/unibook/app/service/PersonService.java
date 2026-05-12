package com.unibook.app.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.model.Person;
import com.unibook.app.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Person
     * @param name
     * @param email
     * @return PersonResponse
    **/
    public PersonResponse createPerson(String name, String email) {
        var person = new Person();
        person.setName(name);
        person.setEmail(email);
        return toResponse(personRepository.save(person));
    }

    // TODO: Review if this method is really necessary

    /**
     * Create Person and return their entity
     * @param name
     * @param email
     * @return Person
     */
    public Person createPersonEntity(String name, String email) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        return personRepository.save(person);
    }

    /**
     * Delete Person by id
     * @param id
     */
    public void deleteById(Long id) {
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        personRepository.delete(person);
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch All "Persons" and return a list of PersonResponse
     * @return List<PersonResponse>
     */
    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream()
            .map(this::toResponse)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Fetch All "Persons"
     * @return List<Person>
     */
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Find Person by id
     * @param id 
     * @return PersonResponse
     */
    public PersonResponse findById(Long id) {
        return toResponse(personRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Person not found with id: " + id)));
    }

    /**
     * Find Person by name
     * @param name
     * @return Optional<PersonResponse>
     */
    public Optional<PersonResponse> findByName(String name) {
        return personRepository.findByName(name).map(this::toResponse);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Person instance to PersonResponse
     * @param person
     * @return PersonResponse
     */
    private PersonResponse toResponse(Person person) {
        PersonResponse response = new PersonResponse();
        response.setName(person.getName());
        response.setEmail(person.getEmail());
        return response;
    }
    
}
