package com.nicolasblackson.statacaster.domain.users;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.attribute.repos.AttributeRepo;
import com.nicolasblackson.statacaster.domain.attribute.services.AttributeService;
import com.nicolasblackson.statacaster.domain.user.models.Users;
import com.nicolasblackson.statacaster.domain.user.repos.UserRepo;
import com.nicolasblackson.statacaster.domain.user.services.UserService;
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
public class UserServiceImplTest {

    @MockBean
    private UserRepo mockUserRepo;

    @Autowired
    private UserService userService;

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
    @DisplayName("User Service: Create User - Success")
    public void createUserTestSuccess() throws Exception {
        BDDMockito.doReturn(mockUserResponse1).when(mockUserRepo).save(ArgumentMatchers.any());
        Users returnedUser = userService.create(inputUser);
        Assertions.assertNotNull(returnedUser, "User should not be null");
        Assertions.assertEquals(returnedUser.getId(),1l );
    }

    @Test
    @DisplayName("User Service: Get User by Id - Success")
    public void getUserByIdTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockUserResponse1)).when(mockUserRepo).findById(1l);
        Users foundUser = userService.getUserById(1l);
        Assertions.assertEquals(mockUserResponse1.toString(), foundUser.toString());
    }

    @Test
    @DisplayName("User Service: Get User by Id - Fail")
    public void getUserByIdTestFailed() {
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1l);
        Assertions.assertThrows(Exception.class, () -> {
            userService.getUserById(1l);
        });
    }

    @Test
    @DisplayName("User Service: Get All Users - Success")
    public void getAllUserTestSuccess(){
        List<Users> users = new ArrayList<>();
        users.add(mockUserResponse1);
        users.add(mockUserResponse1);

        BDDMockito.doReturn(users).when(mockUserRepo).findAll();

        List<Users> responseUsers = userService.getAllUsers();
        Assertions.assertIterableEquals(users,responseUsers);
    }

    @Test
    @DisplayName("User Service: Update User - Success")
    public void updateUserTestSuccess() throws Exception {
        Users expectedUserUpdate = new Users("upadtedNicolas", "Blackson1", "nick1");

        BDDMockito.doReturn(Optional.of(mockUserResponse1)).when(mockUserRepo).findById(1l);
        BDDMockito.doReturn(expectedUserUpdate).when(mockUserRepo).save(ArgumentMatchers.any());

        Users actualUser = userService.updateUser(1l, expectedUserUpdate);
        Assertions.assertEquals(expectedUserUpdate.toString(), actualUser.toString());
    }

    @Test
    @DisplayName("User Service: Update User - Fail")
    public void updateUserTestFail()  {
        Users expectedUserUpdate = new Users("upadtedNicolas", "Blackson1", "nick1");

        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1l);
        Assertions.assertThrows(Exception.class, ()-> {
            userService.updateUser(1l, expectedUserUpdate);
        });
    }

    @Test
    @DisplayName("User Service: Delete User - Success")
    public void deleteUserTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockUserResponse1)).when(mockUserRepo).findById(1l);
        Boolean actualResponse = userService.deleteUser(1l);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("User Service: Delete User - Fail")
    public void deleteUserTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1l);
        Assertions.assertThrows(Exception.class, ()-> {
            userService.deleteUser(1l);
        });
    }
}
