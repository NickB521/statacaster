package com.nicolasblackson.statacaster.domain.attribute.services;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;

import java.util.List;


public interface AttributeService {
    Attribute create(Attribute attribute) throws ResourceCreationException;
    Attribute getAttributeById(Long id) throws ResourceNotFoundException;
    List<Attribute> getAllAttributes();
    Attribute updateAttribute(Long id, Attribute attributeDetails) throws Exception;
    Boolean deleteAttribute(Long id) throws ResourceNotFoundException;
}
