package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.dto.LoginRequestDto;
import ca.b3nk3lly.wedding_website_backend.dto.LoginResponseDto;
import ca.b3nk3lly.wedding_website_backend.service.AuthenticationService;
import ca.b3nk3lly.wedding_website_backend.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @MockitoBean
    private AuthenticationService authService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLoginWithGoodCredentialsIsSuccessful() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder().username("brkelly").password("abc123").build();
        Mockito.when(authService.authenticate(authenticatedUser.getUsername(), authenticatedUser.getPassword())).thenReturn(authenticatedUser);

        String token = "the-token";
        Mockito.when(jwtService.generateToken(any())).thenReturn(token);

        Date expiry = new Date();
        Mockito.when(jwtService.extractExpiration(token)).thenReturn(expiry);

        LoginRequestDto loginRequest = LoginRequestDto.builder().username(authenticatedUser.getUsername()).password(authenticatedUser.getPassword()).build();

        ResponseEntity<LoginResponseDto> response = restTemplate.postForEntity("/auth/login", loginRequest, LoginResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody().token());
        assertEquals(expiry, response.getBody().expiresAt());
    }

    @Test
    void testLoginWithBadCredentialsFails() {
        Mockito.when(authService.authenticate(any(), any())).thenThrow(BadCredentialsException.class);

        LoginRequestDto loginRequest = LoginRequestDto.builder().username("username").password("password").build();

        ResponseEntity<?> response = restTemplate.postForEntity("/auth/login", loginRequest, String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
