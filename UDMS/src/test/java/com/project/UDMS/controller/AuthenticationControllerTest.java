package com.project.UDMS.controller;

import com.project.UDMS.controler.AuthenticationController;
import com.project.UDMS.entity.UserEntity;
import com.project.UDMS.resource.Role;
import com.project.UDMS.response.AuthenticationResponse;
import com.project.UDMS.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class
AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        UserEntity request = new UserEntity();
        AuthenticationResponse authResponse = new AuthenticationResponse("xyz");

        when(authenticationService.register(any(UserEntity.class))).thenReturn(authResponse);
        when(authenticationService.findRole(any(UserEntity.class))).thenReturn(Role.STUDENT);

        ResponseEntity<Map<String, Object>> result = authenticationController.register(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(authResponse, result.getBody().get("authResponse"));
        assertEquals(Role.STUDENT, result.getBody().get("role"));
    }

    @Test
    public void testLoginSuccess() {
        UserEntity request = new UserEntity();
        AuthenticationResponse authResponse = new AuthenticationResponse("xyz");

        when(authenticationService.register(any(UserEntity.class))).thenReturn(authResponse);
        when(authenticationService.findRole(any(UserEntity.class))).thenReturn(Role.STUDENT);

        ResponseEntity<Map<String, Object>> result = authenticationController.login(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(authResponse, result.getBody().get("authResponse"));
        assertEquals(Role.STUDENT, result.getBody().get("role"));
    }


    @Test
    public void testRegisterUserAlreadyExists() {
        UserEntity request = new UserEntity();
        when(authenticationService.register(any(UserEntity.class))).thenThrow(new RuntimeException("User already exists"));
        try {
            authenticationController.register(request);
        } catch (RuntimeException e) {
            assertEquals("User already exists", e.getMessage());
        }
    }

    @Test
    public void testLoginUserNotFound() {
        UserEntity request = new UserEntity();
        when(authenticationService.login(any(UserEntity.class))).thenThrow(new RuntimeException("User not found"));
        try {
            authenticationController.login(request);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
    }
}
