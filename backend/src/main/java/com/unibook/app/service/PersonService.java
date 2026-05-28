package com.unibook.app.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.person.CreatePersonRequest;
import com.unibook.app.dto.request.person.UpdatePersonRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.PersonMapper;
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
    public PersonResponse createPerson(CreatePersonRequest request) {
        var person = new Person();
        person.setName(request.getName());
        person.setEmail(request.getEmail());
        person.setBirthDate(request.getBirthDate());
        return PersonMapper.toResponse(personRepository.save(person));
    }

    /**
     * Delete Person by id
     * @param id
     */
    public void deleteById(Long id) {
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        person.softDelete();
        personRepository.save(person);
    }

    /**
     * Restore Person by id
     * @param id
     * @return PersonResponse
     */
    public PersonResponse restoreById(Long id) {
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        person.restore();
        return PersonMapper.toResponse(personRepository.save(person));
    }

    /**
     * Update person
     * @param id
     * @param request
     * @param partial
     * @return PersonResponse
     */
    public PersonResponse update(Long id, UpdatePersonRequest request, boolean partial){
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Person not Found"));
        
        if(!partial || request.getName() != null){
            person.setName(request.getName());
        }

        if(!partial || request.getEmail() != null){
            person.setEmail(request.getEmail());
        }

        if(!partial || request.getBirthDate() != null){
            person.setBirthDate(request.getBirthDate());
        }

        return PersonMapper.toResponse(personRepository.save(person));
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
            .map(PersonMapper::toResponse)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find Person by id
     * @param id 
     * @return PersonResponse
     */
    public PersonResponse findById(Long id) {
        return PersonMapper.toResponse(personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id)));
    }

    /**
     * Find Person by name
     * @param name
     * @return Optional<PersonResponse>
     */
    public Optional<PersonResponse> findByName(String name) {
        return personRepository.findByName(name).map(PersonMapper::toResponse);
    }

    
}
