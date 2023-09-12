package com.nicolasblackson.statacaster.domain.attribute.repos;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.user.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeRepo extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findByAttribute(String attributeName);
}
