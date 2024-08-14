package com.project.UDMS.service;

import com.project.UDMS.entity.UserEntity;
import com.project.UDMS.repository.UserRepository;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import com.project.UDMS.repository.ProfessorRepository;
import com.project.UDMS.resource.Role;
import com.project.UDMS.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setUserId("S30");
        user.setUsername("sharat");
        user.setPassword("password");
    }

    @Test
    public void testRegisterUsernameTaken() {
        when(userRepository.existsByUsername("sharat")).thenReturn(true);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("Username is already taken!", response.getToken());
        verify(userRepository, never()).existsByPassword(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterPasswordTaken() {
        when(userRepository.existsByUsername("sharat")).thenReturn(false);
        when(userRepository.existsByPassword("password")).thenReturn(true);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("Password is already taken!", response.getToken());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterUserIdTaken() {
        when(userRepository.existsByUserId("S30")).thenReturn(true);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("UserId is already having an account!", response.getToken());
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).existsByPassword(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterStudentNotEnrolled() {
        user.setRole(Role.STUDENT);
        when(userRepository.existsByUserId("S30")).thenReturn(false);
        when(studentEnrollmentRepository.existsById("S30")).thenReturn(false);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("This RegNo is Not admitted to University", response.getToken());
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).existsByPassword(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterProfessorNotFaculty() {
        user.setRole(Role.PROFESSOR);
        when(userRepository.existsByUserId("S30")).thenReturn(false);
        when(professorRepository.existsById("S30")).thenReturn(false);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("This ProfessorId is Not a Faculty member of this University", response.getToken());
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).existsByPassword(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterSuccess() {
        when(userRepository.existsByUsername("sharat")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(user);

        assertNotNull(response.getToken());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(jwtService, times(1)).generateToken(any(UserEntity.class));
    }

    @Test
    public void testLoginSuccess() {
        UserDetails userDetails = User.withUsername("sharat").password("password").roles("USER").build();
        when(userRepository.findByUsername("sharat")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.login(userDetails);

        assertNotNull(response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(UserEntity.class));
    }

    @Test
    public void testLoginFailure() {
        UserDetails userDetails = User.withUsername("sharat").password("wrongPassword").roles("USER").build();
        when(userRepository.findByUsername("sharat")).thenReturn(Optional.of(user));
        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.login(userDetails);
        });

        assertEquals("Bad credentials", exception.getMessage());
        verify(jwtService, never()).generateToken(any(UserEntity.class));
    }
}
