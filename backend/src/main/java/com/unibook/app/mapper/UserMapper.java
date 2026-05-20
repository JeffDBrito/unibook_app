package com.unibook.app.mapper;

import com.unibook.app.dto.request.user.PartialUpdateUserRequest;
import com.unibook.app.dto.request.user.UpdateUserRequest;
import com.unibook.app.dto.response.PersonResponse;
import com.unibook.app.dto.response.UserResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.model.Role;
import com.unibook.app.model.User;
import com.unibook.app.repository.UserRepository;

public class UserMapper {

    private static UserRepository userRepository;
    
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

        String roleTitles = user.getRoles().stream()
            .map(Role::getTitle)
            .reduce((a, b) -> a + ", " + b)
            .orElse("No Roles");
        System.out.println("Mapping user to response: " + user.getLogin() + ", roles: " + roleTitles);
        response.setRoles(roleTitles);

        return response;
    }

    /**
     * Receive UserResponse and returns an User By Id
     * @param response
     * @return User
     * @throws ResourceNotFoundException
     */
    public static User toInstance(UserResponse response){        
        User user = userRepository.findById(response.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User Not found"));

        return user;
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
