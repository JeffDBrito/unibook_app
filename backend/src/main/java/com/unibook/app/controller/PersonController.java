package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.person.CreatePersonRequest;
import com.unibook.app.dto.request.person.UpdatePersonRequest;
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
        return personService.createPerson(request);
    }

    // Get person by id
    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID", description = "Retrieves a person by their unique ID and returns the person details.", tags = {"Person Endpoints"})
    public PersonResponse getPersonById(@PathVariable Long id) {
        return personService.findById(id);
    }

    // Delete by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete person by ID", description = "Deletes a person by their unique ID and returns a confirmation message.", tags = {"Person Endpoints"})
    public void deletePerson(@PathVariable Long id) {
        personService.deleteById(id);        
    }

    // Get person by name
    @GetMapping("/name/{name}")
    @Operation(summary = "Get person by name", description = "Retrieves a person by their name and returns the person details.", tags = {"Person Endpoints"})
    public PersonResponse getPersonByName(@PathVariable String name) {
        return personService.findByName(name)
            .orElseThrow(() -> new RuntimeException("Person not found with name: " + name));
    }

    // Partial update
    @PatchMapping("/{id}")
    @Operation(summary = "Partial update Person", description = "Partially updates an existing Person with the provided details and returns the updated Person.", tags = {"Person Endpoints"})
    public PersonResponse partialUpdate(@PathVariable Long id, @RequestBody UpdatePersonRequest request){
        return personService.update(id, request, true);
    }

    // Full update
    @PutMapping("/{id}")
    @Operation(summary = "Partial update Person", description = "Updates an existing Person with the provided details and returns the updated Person.", tags = {"Person Endpoints"})
    public PersonResponse fullUpdate(@PathVariable Long id, @RequestBody UpdatePersonRequest request){
        return personService.update(id, request, false);
    }

    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore Person by id", description = "Restores a previously deleted Person by their id and returns the restored Person details.", tags = {"Person Endpoints"})
    public PersonResponse restore(@PathVariable Long id){
        return personService.restoreById(id);
    }
}
