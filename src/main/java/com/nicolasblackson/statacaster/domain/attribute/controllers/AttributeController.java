package com.nicolasblackson.statacaster.domain.attribute.controllers;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.attribute.services.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attribute")
public class AttributeController {
    private AttributeService attributeService;
    @Autowired
    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    public ResponseEntity<List<Attribute>> getAll(){
        List<Attribute> attributes = attributeService.getAllAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Attribute> create(@RequestBody Attribute attribute) throws Exception {
        attribute = attributeService.create(attribute);
        return new ResponseEntity<>(attribute, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Attribute> getAttributeById(@PathVariable("id") Long id) throws Exception {
        Attribute attribute = attributeService.getAttributeById(id);
        return new ResponseEntity<>(attribute, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Attribute> updateAttribute(@PathVariable("id") Long id, @RequestBody Attribute attributeDetails){
        try{
            Attribute updateAttribute = attributeService.updateAttribute(id, attributeDetails);
            ResponseEntity response = new ResponseEntity(updateAttribute, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAttribute(@PathVariable("id") Long id){
        try {
            attributeService.deleteAttribute(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
