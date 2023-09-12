package com.nicolasblackson.statacaster.domain.action.repos;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionRepo extends JpaRepository<Action, Long> {
    Optional<Action> findByAction(String actionName);

}
