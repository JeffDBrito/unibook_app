package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.author.CreateAuthorRequest;
import com.unibook.app.dto.request.author.PartialUpdateAuthorRequest;
import com.unibook.app.dto.request.author.UpdateAuthorRequest;
import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.AuthorMapper;
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
     */
    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        Person person = new Person();
        person.setName(request.getName());
        person.setBirthDate(request.getBirthDate());
        
        person = personRepository.save(person);
        
        Author author = new Author();
        author.setBiography(request.getBiography());
        author.setPerson(person);
        
        Author savedAuthor = authorRepository.save(author);
        return AuthorMapper.toResponse(savedAuthor);
    }

    /**
     * Soft Delete Author by id
     * @param id
     */
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));        
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
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));        
        author.restore();
        return AuthorMapper.toResponse(authorRepository.save(author));
    }

    /**
     * Partially update Author and return the Author updated
     * @param id
     * @param request
     * @param partial
     * @return
     */
    public AuthorResponse update(Long id, PartialUpdateAuthorRequest request, boolean partial){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        Person person = author.getPerson();

        if(!partial || request.getName() != null){
            person.setName(request.getName());
        }

        if(!partial || request.getBiography() != null){
            author.setBiography(request.getBiography());
        }
        
        personRepository.save(person);

        return AuthorMapper.toResponse(authorRepository.save(author));
    }

    public AuthorResponse update(Long id, UpdateAuthorRequest request){
        return this.update(id, AuthorMapper.toPartialUpdate(request), false);
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
        return authors.stream().map(AuthorMapper::toResponse).toList();
    }

    /**
     * Find Author by id
     * @param id
     * @return AuthorResponse
     */
    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return AuthorMapper.toResponse(author);
    }

    /**
     * Find Author by name
     * @param name
     * @return AuthorResponse
     */
    public AuthorResponse findByName(String name) {
        Author author = authorRepository.findByPersonName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with name: " + name));
        return AuthorMapper.toResponse(author);
    }
    
}
