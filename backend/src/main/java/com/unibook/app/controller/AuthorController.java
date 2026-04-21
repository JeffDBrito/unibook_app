package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // List authors
    @GetMapping
    public List<AuthorResponse> getAllAuthors() {
        return authorService.findAll();
    }

    // Create author
    @PostMapping
    public AuthorResponse createAuthor(@RequestBody AuthorResponse request) {
        return authorService.createAuthor(
            request.getName(), 
            request.getBiography()
        );
    }   

    // Get author by id
    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    // Get author by name
    @GetMapping("/name/{name}")
    public AuthorResponse getAuthorByName(@PathVariable String name) {
        return authorService.findByName(name);
    }

}
