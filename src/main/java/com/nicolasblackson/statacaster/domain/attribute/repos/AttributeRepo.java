package com.nicolasblackson.statacaster.domain.attribute.repos;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.user.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepo extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findByAttributeName(String attributeName);
}
