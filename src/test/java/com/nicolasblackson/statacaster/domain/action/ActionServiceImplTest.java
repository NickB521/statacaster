package com.nicolasblackson.statacaster.domain.action;

import com.nicolasblackson.statacaster.domain.action.models.Action;
import com.nicolasblackson.statacaster.domain.action.repos.ActionRepo;
import com.nicolasblackson.statacaster.domain.action.services.ActionService;
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
public class ActionServiceImplTest {

    @MockBean
    private ActionRepo mockActionRepo;

    @Autowired
    private ActionService actionService;

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
    @DisplayName("Action Service: Create Action - Success")
    public void createActionTestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseAction1).when(mockActionRepo).save(ArgumentMatchers.any());
        Action returnedAction = actionService.create(inputAction);
        Assertions.assertNotNull(returnedAction, "Action should not be null");
        Assertions.assertEquals(returnedAction.getId(),1l );
    }

    @Test
    @DisplayName("Action Service: Get Action by Id - Success")
    public void getActionByIdTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseAction1)).when(mockActionRepo).findById(1l);
        Action foundAction = actionService.getActionById(1l);
        Assertions.assertEquals(mockResponseAction1.toString(), foundAction.toString());
    }

    @Test
    @DisplayName("Action Service: Get Action by Id - Fail")
    public void getActionByIdTestFailed() {
        BDDMockito.doReturn(Optional.empty()).when(mockActionRepo).findById(1l);
        Assertions.assertThrows(Exception.class, () -> {
            actionService.getActionById(1l);
        });
    }

    @Test
    @DisplayName("Action Service: Get All Actions - Success")
    public void getAllActionTestSuccess(){
        List<Action> actions = new ArrayList<>();
        actions.add(mockResponseAction1);
        actions.add(mockResponseAction2);

        BDDMockito.doReturn(actions).when(mockActionRepo).findAll();

        List<Action> responseActions = actionService.getAllActions();
        Assertions.assertIterableEquals(actions,responseActions);
    }

    @Test
    @DisplayName("Action Service: Update Action - Success")
    public void updateActionTestSuccess() throws Exception {
        Action expectedActionUpdate = new Action("updatedRunning");

        BDDMockito.doReturn(Optional.of(mockResponseAction1)).when(mockActionRepo).findById(1l);
        BDDMockito.doReturn(expectedActionUpdate).when(mockActionRepo).save(ArgumentMatchers.any());

        Action actualAction = actionService.updateAction(1l, expectedActionUpdate);
        Assertions.assertEquals(expectedActionUpdate.toString(), actualAction.toString());
    }

    @Test
    @DisplayName("Action Service: Update Action - Fail")
    public void updateActionTestFail()  {
        Action expectedActionUpdate = new Action("updatedRunning");

        BDDMockito.doReturn(Optional.empty()).when(mockActionRepo).findById(1l);
        Assertions.assertThrows(Exception.class, ()-> {
            actionService.updateAction(1l, expectedActionUpdate);
        });
    }

    @Test
    @DisplayName("Action Service: Delete Action - Success")
    public void deleteActionTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseAction1)).when(mockActionRepo).findById(1l);
        Boolean actualResponse = actionService.deleteAction(1l);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Action Service: Delete Action - Fail")
    public void deleteActionTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockActionRepo).findById(1l);
        Assertions.assertThrows(Exception.class, ()-> {
            actionService.deleteAction(1l);
        });
    }


}
