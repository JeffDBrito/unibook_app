package com.unibook.app.mapper;

import com.unibook.app.dto.request.person.PartialUpdatePersonRequest;
import com.unibook.app.dto.request.person.UpdatePersonRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.model.Person;

public class PersonMapper {

    /**
     * Convert Person instance to PersonResponse
     * @param person
     * @return PersonResponse
     */ 
    public static PersonResponse toResponse(Person person) {
        PersonResponse response = new PersonResponse();
        response.setId(person.getId());
        response.setBirthDate(person.getBirthDate());
        response.setName(person.getName());
        response.setEmail(person.getEmail());
        return response;
    }

    /**
     * Convert Full update dto to Partial update dto
     * @param request
     * @return PartialUpdatePersonRequest
     */
    public static PartialUpdatePersonRequest toPartialUpdate(UpdatePersonRequest request){
        PartialUpdatePersonRequest partial = new PartialUpdatePersonRequest();
        partial.setName(request.getName());
        partial.setEmail(request.getEmail());
        partial.setBirthDate(request.getBirthDate());
        
        return partial;
    }

}
