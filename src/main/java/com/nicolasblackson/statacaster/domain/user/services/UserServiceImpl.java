package com.nicolasblackson.statacaster.domain.user.services;

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
    public Users create(Users user) throws Exception {
        Optional<Users> optional = userRepo.findByUser(user.getFirstName());
        if(optional.isPresent())
            throw new Exception("User already exist: " + user.getFirstName());
        user = userRepo.save(user);
        return user;
    }

    @Override
    public Users getUserById(Long id) throws Exception {
        Users user = userRepo.findById(id)
                .orElseThrow(()->new Exception("No User with id: " + id));
        return user;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Users updateUser(Long id, Users userDetails) throws Exception {
        Users user = getUserById(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setNickName(userDetails.getNickName());
        user = userRepo.save(user);
        return user;
    }

    @Override
    public Boolean deleteUser(Long id) throws Exception {
        Optional<Users> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new Exception("User does not exists, can not delete");
        }
        Users userToDelete = userOptional.get();
        userRepo.delete(userToDelete);
        return true;
    }
}
