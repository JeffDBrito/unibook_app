package com.unibook.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.author.UpdateAuthorRequest;
import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.model.Author;
import com.unibook.app.model.Person;
import com.unibook.app.repository.AuthorRepository;
import com.unibook.app.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PersonRepository personRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Author
     * @param name
     * @param biography
     * @return AuthorResponse
     */ //TODO: User CreateRequest dto
    public AuthorResponse createAuthor(String name, String biography, LocalDate birthDate) {
        Person person = new Person();
        person.setName(name);
        person.setBirthDate(birthDate);
        
        person = personRepository.save(person);
        
        Author author = new Author();
        author.setBiography(biography);
        author.setPerson(person);
        
        Author savedAuthor = authorRepository.save(author);
        return toResponse(savedAuthor);
    }

    /**
     * Soft Delete Author by id
     * @param id
     */
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));        
        author.softDelete();
        authorRepository.save(author);
    }

    /**
     * Restore Author by id
     * @param id
     * @return AuthorResponse
     */
    public AuthorResponse restoreById(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));        
        author.restore();
        return toResponse(authorRepository.save(author));
    }

    /**
     * Partially update Author and return the Author updated
     * @param id
     * @param request
     * @param partial
     * @return
     */
    public AuthorResponse update(Long id, UpdateAuthorRequest request, boolean partial){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found"));

        Person person = author.getPerson();

        if(!partial || request.getName() != null){
            person.setName(request.getName());
        }

        if(!partial || request.getBiography() != null){
            author.setBiography(request.getBiography());
        }
        
        personRepository.save(person);

        return toResponse(authorRepository.save(author));
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all Authors
     * @return List<AuthorResponse>
     */
    public List<AuthorResponse> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::toResponse).toList();
    }

    /**
     * Find Author by id
     * @param id
     * @return AuthorResponse
     */
    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return toResponse(author);
    }

    /**
     * Find Author by name
     * @param name
     * @return AuthorResponse
     */
    public AuthorResponse findByName(String name) {
        Author author = authorRepository.findByPersonName(name)
            .orElseThrow(() -> new RuntimeException("Author not found with name: " + name));
        return toResponse(author);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Author instance to AuthorResponse dto
     * @param author
     * @return AuthorResponse
     */ // TODO: Create a Mapper
    private AuthorResponse toResponse(Author author) {
        
        PersonResponse person = new PersonResponse();
        person.setName(author.getPerson().getName());
        person.setEmail(author.getPerson().getEmail());
        
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setBiography(author.getBiography());
        response.setPerson(person);
        
        return response;   
    }
    
}
