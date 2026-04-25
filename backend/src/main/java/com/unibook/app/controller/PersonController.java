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

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/persons")
public class PersonController {
    
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    // List categories
    @GetMapping
    @Operation(summary = "List persons", description = "Retrieves a list of all persons and returns their details.", tags = {"Person Endpoints"})
    public List<PersonResponse> getAllCategories() {
        return personService.findAll();
    }

    // Create person
    @PostMapping
    @Operation(summary = "Create a new person", description = "Creates a new person with the provided details and returns the created person.", tags = {"Person Endpoints"})
    public PersonResponse createPerson(@RequestBody CreatePersonRequest request) {
        return personService.createPerson(
            request.getName(),
            request.getEmail()
        );
    }

    // Get person by id
    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID", description = "Retrieves a person by their unique ID and returns the person details.", tags = {"Person Endpoints"})
    public PersonResponse getPersonById(@PathVariable Long id) {
        return personService.findById(id);
    }

    // Get person by name
    @GetMapping("/name/{name}")
    @Operation(summary = "Get person by name", description = "Retrieves a person by their name and returns the person details.", tags = {"Person Endpoints"})
    public PersonResponse getPersonByName(@PathVariable String name) {
        return personService.findByName(name)
                .orElseThrow(() -> new RuntimeException("Person not found with name: " + name));
    }
}
