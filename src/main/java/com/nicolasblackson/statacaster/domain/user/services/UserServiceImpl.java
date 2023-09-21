package com.nicolasblackson.statacaster.domain.user.services;

import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import com.nicolasblackson.statacaster.domain.user.models.Users;
import com.nicolasblackson.statacaster.domain.user.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    @Override
    public Users create(Users user) throws ResourceCreationException {
        Optional<Users> optional = userRepo.findByFirstName(user.getFirstName());
        if(optional.isPresent())
            throw new ResourceCreationException("User already exist: " + user.getFirstName());
        user = userRepo.save(user);
        return user;
    }

    @Override
    public Users getUserById(Long id) throws ResourceNotFoundException {
        Users user = userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No User with id: " + id));
        return user;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Users updateUser(Long id, Users userDetails) throws ResourceNotFoundException {
        Users user = getUserById(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setNickName(userDetails.getNickName());
        user = userRepo.save(user);
        return user;
    }

    @Override
    public Boolean deleteUser(Long id) throws ResourceNotFoundException {
        Optional<Users> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new ResourceNotFoundException("User does not exists, can not delete");
        }
        Users userToDelete = userOptional.get();
        userRepo.delete(userToDelete);
        return true;
    }
}
