package com.project.UDMS.repository;

import com.project.UDMS.entity.UserEntity;
import com.project.UDMS.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setUserId("S30");
        user.setUsername("sharat");
        user.setPassword("123456");
        user.setRole(Role.ADMIN);
    }

    @Test
    public void testFindByUsernameSuccess() {
        when(userRepository.findByUsername("sharat")).thenReturn(Optional.of(user));
        Optional<UserEntity> foundUser = userRepository.findByUsername("sharat");
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
        Optional<UserEntity> foundUser = userRepository.findByUsername("nonexistentuser");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testSaveUser() {
        UserEntity newUser = new UserEntity();
        newUser.setUserId("abc");
        newUser.setUsername("xyz");
        newUser.setRole(Role.ADMIN);
        newUser.setPassword("password123");
        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);
        UserEntity savedUser = userRepository.save(newUser);
        assertNotNull(savedUser.getId());
        assertEquals("abc", savedUser.getUserId());
        assertEquals("xyz", savedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testExistsByUsernameTrue() {
        when(userRepository.existsByUsername("sharat")).thenReturn(true);
        assertTrue(userRepository.existsByUsername("sharat"));
    }

    @Test
    public void testExistsByUsernameFalse() {
        when(userRepository.existsByUsername("nonexistentuser")).thenReturn(false);
        assertFalse(userRepository.existsByUsername("nonexistentuser"));
    }

    @Test
    public void testExistsByPasswordTrue() {
        when(userRepository.existsByPassword("123456")).thenReturn(true);
        assertTrue(userRepository.existsByPassword("123456"));
    }

    @Test
    public void testExistsByPasswordFalse() {
        when(userRepository.existsByPassword("nonexistent@example.com")).thenReturn(false);
        assertFalse(userRepository.existsByPassword("nonexistent@example.com"));
    }

    @Test
    public void testExistsByUserIdTrue() {
        when(userRepository.existsByPassword("S30")).thenReturn(true);
        assertTrue(userRepository.existsByPassword("S30"));
    }

    @Test
    public void testExistsByUserIdFalse() {
        when(userRepository.existsByPassword("S30")).thenReturn(false);
        assertFalse(userRepository.existsByPassword("S30"));
    }
}
