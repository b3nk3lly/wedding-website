package ca.b3nk3lly.wedding_website_backend.config;

import ca.b3nk3lly.wedding_website_backend.domain.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(RoleType.ADMIN.toString()).implies(RoleType.USER.toString())
                .build();
    }
}
