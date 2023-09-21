package com.nicolasblackson.statacaster.domain.user.services;

import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import com.nicolasblackson.statacaster.domain.user.models.Users;

import java.util.List;

public interface UserService {
    Users create(Users user) throws ResourceCreationException;
    Users getUserById(Long id) throws ResourceNotFoundException;
    List<Users> getAllUsers();
    Users updateUser(Long id, Users userDetails) throws ResourceNotFoundException;
    Boolean deleteUser(Long id) throws ResourceNotFoundException;
}
