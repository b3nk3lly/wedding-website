package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.dto.LoginRequestDto;
import ca.b3nk3lly.wedding_website_backend.dto.LoginResponseDto;
import ca.b3nk3lly.wedding_website_backend.service.AuthenticationService;
import ca.b3nk3lly.wedding_website_backend.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        AuthenticatedUser authenticatedUser = authenticationService.authenticate(loginRequest.username(), loginRequest.password());
        String token = jwtService.generateToken(authenticatedUser);
        return LoginResponseDto.builder().token(token).expiresAt(jwtService.extractExpiration(token)).build();
    }
}
