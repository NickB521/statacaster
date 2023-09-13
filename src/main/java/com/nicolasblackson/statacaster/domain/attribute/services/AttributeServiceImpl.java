package com.nicolasblackson.statacaster.domain.attribute.services;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.attribute.repos.AttributeRepo;

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
    public Attribute create(Attribute attribute) throws Exception {
        Optional<Attribute> optional = attributeRepo.findByAttributeName(attribute.getAttributeName());
        if(optional.isPresent())
            throw new Exception("Attribute already exist: " + attribute.getAttributeName());
        attribute = attributeRepo.save(attribute);
        return attribute;
    }

    @Override
    public Attribute getAttributeById(Long id) throws Exception {
        Attribute attribute = attributeRepo.findById(id)
                .orElseThrow(()->new Exception("No Attribute with id: " + id));
        return attribute;
    }

    @Override
    public List<Attribute> getAllAttributes() {
        return attributeRepo.findAll();
    }

    @Override
    public Attribute updateAttribute(Long id, Attribute attributeDetails) throws Exception {
        Attribute attribute = getAttributeById(id);
        attribute.setAttributeName(attribute.getAttributeName());
        attribute.setPoints(attribute.getPoints());
        attribute.setUserId(attribute.getUserId());
        return attribute;
    }

    @Override
    public Boolean deleteAttribute(Long id) throws Exception {
        Optional<Attribute> attributeOptional = attributeRepo.findById(id);
        if(attributeOptional.isEmpty()){
            throw new Exception("Attribute does not exists, can not delete");
        }
        Attribute attributeToDel = attributeOptional.get();
        attributeRepo.delete(attributeToDel);
        return true;
    }
}
