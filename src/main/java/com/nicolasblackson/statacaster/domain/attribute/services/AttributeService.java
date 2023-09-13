package com.nicolasblackson.statacaster.domain.attribute.services;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;

import java.util.List;


public interface AttributeService {
    Attribute create(Attribute attribute) throws Exception;
    Attribute getAttributeById(Long id) throws Exception;
    List<Attribute> getAllAttributes();
    Attribute updateAttribute(Long id, Attribute attributeDetails) throws Exception;
    Boolean deleteAttribute(Long id) throws Exception;
}
