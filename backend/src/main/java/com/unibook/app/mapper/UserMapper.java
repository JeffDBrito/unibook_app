package com.unibook.app.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.unibook.app.dto.request.user.PartialUpdateUserRequest;
import com.unibook.app.dto.request.user.UpdateUserRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;

public class UserMapper {

    private UserMapper() {}
    
    /**
     * Receive User and returns an UserResponse
     * @param user
     * @return UserResponse
     */
    public static UserResponse toResponse(User user){
        PersonResponse person = new PersonResponse();
        person.setName(user.getPerson().getName());
        person.setEmail(user.getPerson().getEmail());
        person.setBirthDate(user.getPerson().getBirthDate());

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setLogin(user.getLogin());
        response.setPerson(person);

        Set<String> roleTitles = user.getRoles().stream().map(Role::getTitle).collect(Collectors.toSet());

        response.setRoles(roleTitles);

        return response;
    }

    /**
     * Converts UpdateRequest to Partial UpdateRequest
     * @param id
     * @param request
     * @param partial
     * @return PartialUpdateUserRequest
     */
    public static PartialUpdateUserRequest toPartialUpdate(UpdateUserRequest request){
        
        PartialUpdateUserRequest partialRequest = new PartialUpdateUserRequest();
        partialRequest.setName(request.getName());
        partialRequest.setEmail(request.getEmail());
        partialRequest.setLogin(request.getLogin());
        partialRequest.setBirthDate(request.getBirthDate());
        partialRequest.setPassword(request.getPassword());
        partialRequest.setRoleIds(request.getRoleIds());

        return partialRequest;
    }

}
