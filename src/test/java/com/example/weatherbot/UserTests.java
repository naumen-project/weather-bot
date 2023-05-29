package com.example.weatherbot;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UserTests {

    @Mock
    User user;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        assertEquals("Correct", null, user.getCity());
    }

    @Test
    public void testGetUserState() {
        when(user.getUserState()).thenReturn(UserState.START);
        assertEquals("Correct", UserState.START, user.getUserState());
    }

    @Test
    public void testVerification(){
        String city = user.getCity();
        verify(user).getCity();
        user.setCity("Moscow");
        verify(user).setCity("Moscow");
        verify(user, times(1)).getCity();
    }
}
