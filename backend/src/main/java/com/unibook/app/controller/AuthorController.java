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

import com.unibook.app.dto.request.author.UpdateAuthorRequest;
import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // List authors
    @GetMapping
    @Operation(summary = "List authors", description = "Retrieves a list of all authors and returns their details.", tags = {"Author Endpoints"})
    public List<AuthorResponse> getAllAuthors() {
        return authorService.findAll();
    }

    // Create author
    @PostMapping
    @Operation(summary = "Create a new author", description = "Creates a new author with the provided details and returns the created author.", tags = {"Author Endpoints"})
    public AuthorResponse createAuthor(@RequestBody AuthorResponse request) {
        return authorService.createAuthor(
            request.getName(), 
            request.getBiography()
        );
    }   

    // Get author by id
    @GetMapping("/{id}")
    @Operation(summary = "Get author by id", description = "Retrieves an author by their id and returns the author details.", tags = {"Author Endpoints"})
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    // Delete by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author by ID", description = "Deletes an author by their unique ID and returns a confirmation message.", tags = {"Author Endpoints"})
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
    }

    // Get author by name
    @GetMapping("/name/{name}")
    @Operation(summary = "Get author by name", description = "Retrieves an author by their name and returns the author details.", tags = {"Author Endpoints"})
    public AuthorResponse getAuthorByName(@PathVariable String name) {
        return authorService.findByName(name);
    }

    // Partial update
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update Author", description = "Partially updates an existing author with the provided details and returns the updated author.", tags = {"Author Endpoints"})
    public AuthorResponse partialUpdate(@PathVariable Long id, UpdateAuthorRequest request){
        return authorService.update(id, request, true);
    }

    // Full update
    @PutMapping("/{id}")
    @Operation(summary = "Update Author", description = "Updates an existing author with the provided details and returns the updated author.", tags = {"Author Endpoints"})
    public AuthorResponse fullUpdate(@PathVariable Long id, UpdateAuthorRequest request){
        return authorService.update(id, request, true);
    }

}
