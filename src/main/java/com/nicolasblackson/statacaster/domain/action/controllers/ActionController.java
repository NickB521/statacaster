package com.nicolasblackson.statacaster.domain.action.controllers;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.action.services.ActionService;
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
    public ResponseEntity<Action> create(@RequestBody Action action) throws Exception {
        action = actionService.create(action);
        return new ResponseEntity<>(action, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Action> getActionById(@PathVariable("id") Long id) throws Exception {
        Action action = actionService.getActionById(id);
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Action> updateAction(@PathVariable("id") Long id, @RequestBody Action actionDetails){
        try{
            Action updateAction = actionService.updateAction(id, actionDetails);
            ResponseEntity response = new ResponseEntity(updateAction, HttpStatus.OK);
            return response;
        } catch (Exception e) {
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
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
