package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.model.Author;
import com.unibook.app.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponse createAuthor(String name, String biography) {
        var author = new Author();
        author.setName(name);
        author.setBiography(biography);
        var savedAuthor = authorRepository.save(author);
        return toResponse(savedAuthor);
    }

    public List<AuthorResponse> findAll() {
        var authors = authorRepository.findAll();
        return authors.stream().map(this::toResponse).toList();
    }

    public AuthorResponse findById(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return toResponse(author);
    }

    public AuthorResponse findByName(String name) {
        var author = authorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Author not found with name: " + name));
        return toResponse(author);
    }

    private AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        response.setBiography(author.getBiography());
        return response;   
    }
    
}
