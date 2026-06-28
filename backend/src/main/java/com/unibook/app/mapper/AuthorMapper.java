package com.unibook.app.mapper;

import com.unibook.app.dto.request.author.PartialUpdateAuthorRequest;
import com.unibook.app.dto.request.author.UpdateAuthorRequest;
import com.unibook.app.dto.response.AuthorResponse;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.model.Author;

public class AuthorMapper {
    
    private AuthorMapper(){}

    /**
     * Convert intance to Response dto
     * @param author
     * @return AuthorResponse
     */
    public static AuthorResponse toResponse(Author author){
        PersonResponse person = new PersonResponse();
        person.setId(author.getPerson().getId());
        person.setName(author.getPerson().getName());
        person.setEmail(author.getPerson().getEmail());
        person.setBirthDate(author.getPerson().getBirthDate());
        
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setBiography(author.getBiography());
        response.setPerson(person);
        
        return response;  
    }

    /**
     * Convert Full Update Request to Partial Update Request
     * @param request
     * @return PartialUpdateAuthorRequest
     */
    public static PartialUpdateAuthorRequest toPartialUpdate(UpdateAuthorRequest request){
        PartialUpdateAuthorRequest partial = new PartialUpdateAuthorRequest();
        partial.setName(request.getName());
        partial.setBiography(request.getBiography());
        partial.setBirthDate(request.getBirthDate());

        return partial;
    }

}
