package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
                .map(this::toAuthenticatedUser)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    private AuthenticatedUser toAuthenticatedUser(User user) {
        return AuthenticatedUser.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword()).authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toSet())).build();
    }
}
