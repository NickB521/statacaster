package com.nicolasblackson.statacaster.domain.attribute.services;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.attribute.repos.AttributeRepo;

import com.nicolasblackson.statacaster.domain.exceptions.ResourceCreationException;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeServiceImpl implements AttributeService {

    private AttributeRepo attributeRepo;
    public AttributeServiceImpl(AttributeRepo attributeRepo){
        this.attributeRepo = attributeRepo;
    }

    @Override
    public Attribute create(Attribute attribute) throws ResourceCreationException {
        Optional<Attribute> optional = attributeRepo.findByAttributeName(attribute.getAttributeName());
        if(optional.isPresent())
            throw new ResourceCreationException("Attribute already exist: " + attribute.getAttributeName());
        attribute = attributeRepo.save(attribute);
        return attribute;
    }

    @Override
    public Attribute getAttributeById(Long id) throws ResourceNotFoundException {
        Attribute attribute = attributeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Attribute with id: " + id));
        return attribute;
    }

    @Override
    public List<Attribute> getAllAttributes() {
        return attributeRepo.findAll();
    }

    @Override
    public Attribute updateAttribute(Long id, Attribute attributeDetails) throws Exception {
        Attribute attribute = getAttributeById(id);
        attribute.setAttributeName(attributeDetails.getAttributeName());
        attribute.setPoints(attributeDetails.getPoints());
        attribute.setUserId(attributeDetails.getUserId());
        attribute = attributeRepo.save(attribute);
        return attribute;
    }

    @Override
    public Boolean deleteAttribute(Long id) throws ResourceNotFoundException {
        Optional<Attribute> attributeOptional = attributeRepo.findById(id);
        if(attributeOptional.isEmpty()){
            throw new ResourceNotFoundException("Attribute does not exists, can not delete");
        }
        Attribute attributeToDel = attributeOptional.get();
        attributeRepo.delete(attributeToDel);
        return true;
    }
}
