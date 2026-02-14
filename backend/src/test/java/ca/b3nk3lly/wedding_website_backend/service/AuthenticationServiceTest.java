package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.config.AuthenticationConfig;
import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = { AuthenticationConfig.class, AuthenticationConfiguration.class, AuthenticationService.class })
class AuthenticationServiceTest {

    @MockitoBean
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authService;

    private static final String USERNAME = "brkelly";
    private static final String RAW_PASSWORD = "abc123";

    @Test
    void testAuthenticateWithGoodCredentialsSucceeds() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder().username(USERNAME).password(passwordEncoder.encode(RAW_PASSWORD)).build();

        Mockito.when(authenticatedUserService.loadUserByUsername(USERNAME)).thenReturn(authenticatedUser);

        AuthenticatedUser response = authService.authenticate(USERNAME, RAW_PASSWORD);

        assertEquals(authenticatedUser, response);
    }

    @Test
    void testAuthenticateWithBadPasswordFails() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder().username(USERNAME).password(passwordEncoder.encode(RAW_PASSWORD)).build();

        Mockito.when(authenticatedUserService.loadUserByUsername(USERNAME)).thenReturn(authenticatedUser);

        assertThrows(BadCredentialsException.class, () -> authService.authenticate(USERNAME, "wrong-password"));
    }
}
