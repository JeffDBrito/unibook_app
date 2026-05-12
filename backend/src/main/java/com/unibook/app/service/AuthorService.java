package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.model.Author;
import com.unibook.app.model.Person;
import com.unibook.app.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PersonService personService;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    public AuthorResponse createAuthor(String name, String biography) {

        Person savedPerson = personService.createPersonEntity(name, null);

        Author author = new Author();
        author.setBiography(biography);
        author.setPerson(savedPerson);
        Author savedAuthor = authorRepository.save(author);
        return toResponse(savedAuthor);
    }

    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        
        authorRepository.delete(author);
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    public List<AuthorResponse> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::toResponse).toList();
    }

    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return toResponse(author);
    }

    public AuthorResponse findByName(String name) {
        Author author = authorRepository.findByPersonName(name)
            .orElseThrow(() -> new RuntimeException("Author not found with name: " + name));

        return toResponse(author);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    private AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getPerson().getName());
        response.setBiography(author.getBiography());
        return response;   
    }
    
}
