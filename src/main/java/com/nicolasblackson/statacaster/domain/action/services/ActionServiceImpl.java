package com.nicolasblackson.statacaster.domain.action.services;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.action.repos.ActionRepo;
import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.attribute.repos.AttributeRepo;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionServiceImpl implements ActionService {

    private ActionRepo actionRepo;
    public ActionServiceImpl(ActionRepo actionRepo){
        this.actionRepo = actionRepo;
    }

    @Override
    public Action create(Action action) throws ResourceCreationException {
        Optional<Action> actionOptional = actionRepo.findByActionName(action.getActionName());
        if(actionOptional.isPresent())
            throw new ResourceCreationException("Action already exist: " + action.getActionName());
        action = actionRepo.save(action);
        return action;
    }

    @Override
    public Action getActionById(Long id) throws ResourceNotFoundException {
        Action action = actionRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Action with id: " + id));
        return action;
    }

    @Override
    public List<Action> getAllActions() {
        return actionRepo.findAll();
    }

    @Override
    public Action updateAction(Long id, Action actionDetails) throws Exception {
        Action action = getActionById(id);
        action.setActionName(actionDetails.getActionName());
        action.setTrait(actionDetails.getTrait());
        action.setAttributeId(actionDetails.getAttributeId());
        action = actionRepo.save(actionDetails);
        return action;

    }

    @Override
    public Boolean deleteAction(Long id) throws ResourceNotFoundException {
        Optional<Action> actionOptional = actionRepo.findById(id);
        if(actionOptional.isEmpty()){
            throw new ResourceNotFoundException("Action does not exists, can not delete");
        }
        Action actionToDel = actionOptional.get();
        actionRepo.delete(actionToDel);
        return true;    }
}
