package com.nicolasblackson.statacaster.domain.action.services;

import com.nicolasblackson.statacaster.domain.action.models.Action;

import java.util.List;

public interface ActionService {
    Action create(Action action) throws Exception;
    Action getActionById(Long id) throws Exception;
    List<Action> getAllActions();
    Action updateAction(Long id, Action actionDetails) throws Exception;
    Boolean deleteAction(Long id) throws Exception;
}
