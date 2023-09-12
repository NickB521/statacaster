package com.nicolasblackson.statacaster.domain.user.services;

import com.nicolasblackson.statacaster.domain.user.models.Users;

import java.util.List;

public interface UserService {
    Users create(Users user) throws Exception;
    Users getUserById(Long id) throws Exception;
    List<Users> getAllUsers();
    Users updateUser(Long id, Users userDetails) throws Exception;
    Boolean deleteUser(Long id) throws Exception;
}
