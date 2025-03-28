package com.example.sportsbooking.controllers;

import com.example.sportsbooking.config.TestSecurityConfig;
import com.example.sportsbooking.models.User;
import com.example.sportsbooking.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class) // Importera säkerhetskonfigurationen för tester
@ActiveProfiles("test") // Använd testprofilen
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldCreateUserWhenValidRequestIsProvided() throws Exception {
        // Given: Ett giltigt användarobjekt
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        // When: En POST-begäran skickas till /api/users
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"email\":\"test@example.com\", \"password\":\"password123\"}"))
                // Then: Användaren skapas och returnerar 201 Created med korrekt data
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void shouldReturnAllUsersWhenRequested() throws Exception {
        // Given: En lista med användare finns i systemet
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        // When: En GET-begäran skickas till /api/users
        mockMvc.perform(get("/api/users"))
                // Then: En lista med användare returneras med status 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    public void shouldReturnUserByIdWhenValidIdIsProvided() throws Exception {
        // Given: En användare med ID 1 finns i systemet
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        Mockito.when(userService.getUserById(eq(1L))).thenReturn(user);

        // When: En GET-begäran skickas till /api/users/1
        mockMvc.perform(get("/api/users/1"))
                // Then: Användaren returneras med status 200 OK och korrekt data
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void shouldUpdateUserWhenValidRequestIsProvided() throws Exception {
        // Given: Ett uppdaterat användarobjekt
        User user = new User();
        user.setUsername("updateduser");
        user.setEmail("updated@example.com");
        user.setPassword("newpassword");

        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);

        // When: En PUT-begäran skickas till /api/users/1
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"updateduser\", \"email\":\"updated@example.com\", \"password\":\"newpassword\"}"))
                // Then: Användaren uppdateras och returnerar status 200 OK med korrekt data
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    public void shouldDeleteUserWhenValidIdIsProvided() throws Exception {
        // Given: En användare med ID 1 finns i systemet
        Mockito.doNothing().when(userService).deleteUser(eq(1L));

        // When: En DELETE-begäran skickas till /api/users/1
        mockMvc.perform(delete("/api/users/1"))
                // Then: Användaren raderas och returnerar status 204 No Content
                .andExpect(status().isNoContent());
    }
}
