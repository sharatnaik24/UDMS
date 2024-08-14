package com.project.UDMS.controler;

import com.project.UDMS.dto.UserDto;
import com.project.UDMS.entity.UserEntity;
import com.project.UDMS.resource.Role;
import com.project.UDMS.response.AuthenticationResponse;
import com.project.UDMS.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserEntity request) {
        AuthenticationResponse authResponse = authenticationService.register(request);
        Role role = authenticationService.findRole(request);

        Map<String, Object> response = new HashMap<>();
        response.put("token", authResponse);
        response.put("role", role);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserEntity request) {
        AuthenticationResponse authResponse = authenticationService.login(request);
        Role role = authenticationService.findRole(request);

        Map<String, Object> response = new HashMap<>();
        response.put("token", authResponse);
        response.put("role", role);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
