package com.nicolasblackson.statacaster.domain.action.services;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ActionService {
    Action create(Action action) throws ResourceCreationException;
    Action getActionById(Long id) throws ResourceNotFoundException;
    List<Action> getAllActions();
    Action updateAction(Long id, Action actionDetails) throws Exception;
    Boolean deleteAction(Long id) throws ResourceNotFoundException;
}
