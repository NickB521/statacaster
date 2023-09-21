package com.nicolasblackson.statacaster.domain.attribute;

import com.nicolasblackson.statacaster.domain.BaseControllerTest;

import com.nicolasblackson.statacaster.domain.attribute.model.Attribute;
import com.nicolasblackson.statacaster.domain.attribute.services.AttributeService;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AttributeControllerTest extends BaseControllerTest{

    @MockBean
    private AttributeService mockAttributeService;

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("POST /attribute - success")
    public void createAttributeRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockAttributeResponse1).when(mockAttributeService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/attribute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputAttribute)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("GET /attribute/1 - Found")
    public void getAttributeByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockAttributeResponse1).when(mockAttributeService).getAttributeById(1l);

        mockMvc.perform(get("/attribute/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("GET /attribute/1 - Not Found")
    public void getAttributeByIdTestFail() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockAttributeService).getAttributeById(1l);
        mockMvc.perform(get("/attribute/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /attribute/1 - Success")
    public void putAttributeTestSuccess() throws Exception{
        Attribute expectedAttributeUpdate = new Attribute("updatedHealth", 1);
        expectedAttributeUpdate.setId(1l);
        BDDMockito.doReturn(expectedAttributeUpdate).when(mockAttributeService).updateAttribute(any(), any());
        mockMvc.perform(put("/attribute/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputAttribute)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributeName", Is.is("updatedHealth")));

    }

    @Test
    @DisplayName("DELETE /attribute/1 - Success")
    public void deleteAttributeTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockAttributeService).deleteAttribute(any());
        mockMvc.perform(delete("/attribute/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /attribute/1 - Not Found")
    public void deleteAttributeTestNotFound() throws Exception{
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockAttributeService).deleteAttribute(any());
        mockMvc.perform(delete("/attribute/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
