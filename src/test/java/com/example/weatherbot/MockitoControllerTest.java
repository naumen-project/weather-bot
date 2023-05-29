package com.example.weatherbot;

import com.example.weatherbot.controller.RESTController;
import com.example.weatherbot.model.User;
import com.example.weatherbot.model.UserStateEntity;
import com.example.weatherbot.repository.UserRepository;
import com.example.weatherbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(RESTController.class)
public class MockitoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testFindAll() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(
           new User(7676765L, "Yekaterinburg", null, null, 2),
                new User(32332424L, "Moscow", null, null, 3)
                ));
        List<User> users = userService.getAllUsers();
        assertEquals("Correct",2, users.size());
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.findUserByChatId(1L)).thenReturn(
                Optional.of(new User(7676765L, "Yekaterinburg", null, null, 2)));
        RESTController restController = new RESTController(userService);
        User user = restController.getUser(1L);
        assertEquals("Correct", 7676765L, user.getChatId());
    }

    @Test
    public void testHomeController() throws Exception {
        when(userService.findUserByChatId(1L)).thenReturn(
                Optional.of(new User(121212L, "Moscow", null, null, 3)));
        RESTController restController = new RESTController(userService);
        User user = restController.getUser(1L);
        String city = user.getCity();
        assertEquals("Correct", "Moscow", city);
    }
}
