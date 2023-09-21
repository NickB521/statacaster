package com.nicolasblackson.statacaster.domain.users;

import com.nicolasblackson.statacaster.domain.BaseControllerTest;
import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.exceptions.ResourceNotFoundException;
import com.nicolasblackson.statacaster.domain.user.models.Users;
import com.nicolasblackson.statacaster.domain.user.services.UserService;
import org.apache.catalina.User;
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
public class UsersControllerTest extends BaseControllerTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    private Users inputUser;
    private Users mockUserResponse1;
    private Users mockUserResponse2;

    @BeforeEach
    public void setUp(){
        inputUser = new Users("Nicolas", "Blackson", "nick");

        mockUserResponse1 = new Users("Nicolas1", "Blackson1", "nick1");
        mockUserResponse1.setId(1l);

        mockUserResponse2 = new Users("Nicolas2", "Blackson2", "nick2");
        mockUserResponse2.setId(2l);
    }

    @Test
    @DisplayName("POST /user - success")
    public void createUserRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockUserResponse1).when(mockUserService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputUser)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("GET /user/1 - Found")
    public void getUserByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockUserResponse1).when(mockUserService).getUserById(1l);

        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("GET /user/1 - Not Found")
    public void getUserByIdTestFail() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockUserService).getUserById(1l);
        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /user/1 - Success")
    public void putUserTestSuccess() throws Exception{
        Users expectedUserUpdate = new Users("updatedNicolas", "Blackson1", "nick1");
        expectedUserUpdate.setId(1l);
        BDDMockito.doReturn(expectedUserUpdate).when(mockUserService).updateUser(any(), any());
        mockMvc.perform(put("/user/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputUser)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("updatedNicolas")));

    }

    @Test
    @DisplayName("DELETE /user/1 - Success")
    public void deleteUserTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockUserService).deleteUser(any());
        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /user/1 - Not Found")
    public void deleteUserTestNotFound() throws Exception{
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockUserService).deleteUser(any());
        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
