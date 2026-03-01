package ca.b3nk3lly.wedding_website_backend.util;

import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.entity.Group;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {

    public static boolean canUserAccessGroup(Group group) {
        AuthenticatedUser user = getAuthenticatedUser();
        return user != null && (user.isAdmin() || Objects.equals(user.getId(), group.getUser().getId()));
    }

    private static AuthenticatedUser getAuthenticatedUser() {
        // Get the Security Context for the current thread
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return null;
        }

        // Get the Authentication object from the context
        Authentication authentication = context.getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthenticatedUser authenticatedUser) {
            return authenticatedUser;
        } else {
            // Handle cases where the principal might be a String (e.g., for anonymous users)
            return null;
        }
    }
}
