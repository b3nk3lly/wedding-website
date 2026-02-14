package ca.b3nk3lly.wedding_website_backend.factory;

import ca.b3nk3lly.wedding_website_backend.annotation.WithMockAuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WithMockAuthenticatedUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthenticatedUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockAuthenticatedUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 1. Create your specific Principal object
        AuthenticatedUser principal = AuthenticatedUser.builder().id(annotation.userId()).username(annotation.username()).build();

        // 2. Map the roles from the annotation
        var authorities = Arrays.stream(annotation.roles())
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toList());

        // 3. Create the Authentication object and set it in the context

        PreAuthenticatedAuthenticationToken auth =
                new PreAuthenticatedAuthenticationToken(principal, "N/A", authorities);
        auth.setAuthenticated(true);
        context.setAuthentication(auth);
        return context;
    }
}