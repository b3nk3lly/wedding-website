package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final AuthenticatedUserService authenticatedUserService;

    public AuthenticationService(AuthenticationManager authManager, AuthenticatedUserService authenticatedUserService) {
        this.authManager = authManager;
        this.authenticatedUserService = authenticatedUserService;
    }

    public AuthenticatedUser authenticate(String username, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return authenticatedUserService.loadUserByUsername(username);
    }
}
