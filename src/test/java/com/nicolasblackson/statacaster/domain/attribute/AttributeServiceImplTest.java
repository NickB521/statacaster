package com.nicolasblackson.statacaster.domain.attribute;


import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.attribute.repos.AttributeRepo;
import com.nicolasblackson.statacaster.domain.attribute.services.AttributeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AttributeServiceImplTest {

    @MockBean
    private AttributeRepo mockAttributeRepo;

    @Autowired
    private AttributeService attributeService;

    private Attribute inputAttribute;

    private Attribute mockAttributeResponse1;
    private Attribute mockAttributeResponse2;

    @BeforeEach
    public void setUp(){
        inputAttribute = new Attribute("health", 1);

        mockAttributeResponse1 = new Attribute("health1", 1);
        mockAttributeResponse1.setId(1l);

        mockAttributeResponse2 = new Attribute("health2", 1);
        mockAttributeResponse2.setId(2l);
    }

    @Test
    @DisplayName("Attribute Service: Create Attribute - Success")
    public void createAttributeTestSuccess() throws Exception {
        BDDMockito.doReturn(mockAttributeResponse1).when(mockAttributeRepo).save(ArgumentMatchers.any());
        Attribute returnedAttribute = attributeService.create(inputAttribute);
        Assertions.assertNotNull(returnedAttribute, "Attribute should not be null");
        Assertions.assertEquals(returnedAttribute.getId(),1l );
    }

    @Test
    @DisplayName("Attribute Service: Get Attribute by Id - Success")
    public void getAttributeByIdTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockAttributeResponse1)).when(mockAttributeRepo).findById(1l);
        Attribute foundAttribute = attributeService.getAttributeById(1l);
        Assertions.assertEquals(mockAttributeResponse1.toString(), foundAttribute.toString());
    }

    @Test
    @DisplayName("Attribute Service: Get Attribute by Id - Fail")
    public void getAttributeByIdTestFailed() {
        BDDMockito.doReturn(Optional.empty()).when(mockAttributeRepo).findById(1l);
        Assertions.assertThrows(Exception.class, () -> {
            attributeService.getAttributeById(1l);
        });
    }

    @Test
    @DisplayName("Attribute Service: Get All Attribute - Success")
    public void getAllAttributeTestSuccess(){
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(mockAttributeResponse1);
        attributes.add(mockAttributeResponse2);

        BDDMockito.doReturn(attributes).when(mockAttributeRepo).findAll();

        List<Attribute> responseAttribute = attributeService.getAllAttributes();
        Assertions.assertIterableEquals(attributes, responseAttribute);
    }

    @Test
    @DisplayName("Attribute Service: Update Attribute - Success")
    public void updateAttributeTestSuccess() throws Exception {
        Attribute expectedAttributeUpdate = new Attribute("updatedHealth", 1);

        BDDMockito.doReturn(Optional.of(mockAttributeResponse1)).when(mockAttributeRepo).findById(1l);
        BDDMockito.doReturn(expectedAttributeUpdate).when(mockAttributeRepo).save(ArgumentMatchers.any());

        Attribute actualAttribute = attributeService.updateAttribute(1l, expectedAttributeUpdate);
        Assertions.assertEquals(expectedAttributeUpdate.toString(), actualAttribute.toString());
    }

    @Test
    @DisplayName("Attribute Service: Update Attribute - Fail")
    public void updateAttributeTestFail()  {
        Attribute expectedAttributeUpdate = new Attribute("updatedHealth", 1);

        BDDMockito.doReturn(Optional.empty()).when(mockAttributeRepo).findById(1l);
        Assertions.assertThrows(Exception.class, ()-> {
            attributeService.updateAttribute(1l, expectedAttributeUpdate);
        });
    }

    @Test
    @DisplayName("Attribute Service: Delete Attribute - Success")
    public void deleteAttributeTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockAttributeResponse1)).when(mockAttributeRepo).findById(1l);
        Boolean actualResponse = attributeService.deleteAttribute(1l);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Attribute Service: Delete Attribute - Fail")
    public void deleteAttributeTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockAttributeRepo).findById(1l);
        Assertions.assertThrows(Exception.class, ()-> {
            attributeService.deleteAttribute(1l);
        });
    }
}
