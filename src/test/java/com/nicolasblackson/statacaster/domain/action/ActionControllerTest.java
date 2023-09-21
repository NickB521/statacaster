package com.nicolasblackson.statacaster.domain.action;

import com.nicolasblackson.statacaster.domain.BaseControllerTest;
import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.action.services.ActionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ActionControllerTest extends BaseControllerTest {
    @MockBean
    private ActionService mockActionService;

    @Autowired
    private MockMvc mockMvc;

    private Action inputAction;
    private Action mockResponseAction1;
    private Action mockResponseAction2;

    @BeforeEach
    public void setUp(){
        inputAction = new Action("running");

        mockResponseAction1 = new Action("running1");
        mockResponseAction1.setId(1l);
        mockResponseAction2 = new Action("running2");
        mockResponseAction2.setId(2l);
    }

    @Test
    @DisplayName("POST /action - success")
    public void createActionRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseAction1).when(mockActionService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputAction)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("GET /action/1 - Found")
    public void getActionByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseAction1).when(mockActionService).getActionById(1l);

        mockMvc.perform(get("/action/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("GET /action/1 - Not Found")
    public void getActionByIdTestFail() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockActionService).getActionById(1l);
        mockMvc.perform(get("/action/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /action/1 - Success")
    public void putActionTestSuccess() throws Exception{
        Action expectedActionUpdate = new Action("updatedRunning");
        expectedActionUpdate.setId(1l);
        BDDMockito.doReturn(expectedActionUpdate).when(mockActionService).updateAction(any(), any());
        mockMvc.perform(put("/action/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputAction)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actionName", Is.is("updatedRunning")));

    }

    @Test
    @DisplayName("DELETE /action/1 - Success")
    public void deleteActionTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockActionService).deleteAction(any());
        mockMvc.perform(delete("/action/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /action/1 - Not Found")
    public void deleteActionTestNotFound() throws Exception{
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockActionService).deleteAction(any());
        mockMvc.perform(delete("/action/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
