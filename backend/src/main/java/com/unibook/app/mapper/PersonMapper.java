package com.unibook.app.mapper;

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
        response.setBirthDate(person.getBirthDate());
        response.setName(person.getName());
        response.setEmail(person.getEmail());
        return response;
    }

}
