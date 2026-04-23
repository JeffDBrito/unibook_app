package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.CreatePersonRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.service.PersonService;



@RestController
@RequestMapping("/persons")
public class PersonController {
    
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    // List categories
    @GetMapping
    public List<PersonResponse> getAllCategories() {
        return personService.findAll();
    }

    // Create person
    @PostMapping
    public PersonResponse createPerson(@RequestBody CreatePersonRequest request) {
        return personService.createPerson(
            request.getName(),
            request.getEmail()
        );
    }

    // Get person by id
    @GetMapping("/{id}")
    public PersonResponse getPersonById(@PathVariable Long id) {
        return personService.findById(id);
    }

    // Get person by name
    @GetMapping("/name/{name}")
    public PersonResponse getPersonByName(@PathVariable String name) {
        return personService.findByName(name)
                .orElseThrow(() -> new RuntimeException("Person not found with name: " + name));
    }
}
