package ca.b3nk3lly.wedding_website_backend.annotation;

import ca.b3nk3lly.wedding_website_backend.domain.RoleType;
import ca.b3nk3lly.wedding_website_backend.factory.WithMockAuthenticatedUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAuthenticatedUserSecurityContextFactory.class)
public @interface WithMockAuthenticatedUser {
    String username() default "testUser";
    RoleType[] roles() default { RoleType.USER };
    int userId() default 0;
}