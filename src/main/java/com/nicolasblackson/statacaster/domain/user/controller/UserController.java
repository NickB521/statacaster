package com.nicolasblackson.statacaster.domain.user.controller;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import com.nicolasblackson.statacaster.domain.user.models.Users;
import com.nicolasblackson.statacaster.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAll(){
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Users> create(@RequestBody Users user) throws ResourceCreationException {
        user = userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        try{
            Users users = userService.getUserById(id);
            ResponseEntity<?> response = new ResponseEntity<>(users, HttpStatus.OK);
            return response;
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id") Long id, @RequestBody Users userDetails){
        try{
            Users updateUser = userService.updateUser(id, userDetails);
            ResponseEntity response = new ResponseEntity(updateUser, HttpStatus.ACCEPTED);
            return response;
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
