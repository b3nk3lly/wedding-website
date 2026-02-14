package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthenticatedUserServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticatedUserService authenticatedUserService;

    @Test
    void testLoadByExistingUsernameSucceeds() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("brkelly");
        mockUser.setPassword("abc123");

        Mockito.when(userService.findByUsername("brkelly")).thenReturn(Optional.of(mockUser));

        AuthenticatedUser authenticatedUser = authenticatedUserService.loadUserByUsername("brkelly");

        assertEquals(1, authenticatedUser.getId());
        assertEquals("brkelly", authenticatedUser.getUsername());
        assertEquals("abc123", authenticatedUser.getPassword());
    }

    @Test
    void testLoadByNonExistingUsernameFails() {
        Mockito.when(userService.findByUsername("brkelly")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authenticatedUserService.loadUserByUsername("brkelly"));
    }
}
