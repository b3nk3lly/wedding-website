package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService implements UserDetailsService {

    private final UserService userService;

    public AuthenticatedUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthenticatedUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService
                .findByUsername(username)
                .map(user -> AuthenticatedUser.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword()).build())
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }
}
