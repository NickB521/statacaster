package com.nicolasblackson.statacaster.domain.action.controllers;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.action.services.ActionService;
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
@RequestMapping("/action")
public class ActionController {
    private ActionService actionService;
    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public ResponseEntity<List<Action>> getAll(){
        List<Action> actions = actionService.getAllActions();
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Action> create(@RequestBody Action action) throws ResourceCreationException {
        action = actionService.create(action);
        return new ResponseEntity<>(action, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getActionById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        try{
            Action action = actionService.getActionById(id);
            ResponseEntity<?> response = new ResponseEntity<>(action, HttpStatus.OK);
            return response;
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Action> updateAction(@PathVariable("id") Long id, @RequestBody Action actionDetails) throws Exception {
        try{
            Action updateAction = actionService.updateAction(id, actionDetails);
            ResponseEntity response = new ResponseEntity(updateAction, HttpStatus.ACCEPTED);
            return response;
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAction(@PathVariable("id") Long id){
        try {
            actionService.deleteAction(id);
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
