package ca.b3nk3lly.wedding_website_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
@AllArgsConstructor
public class AuthenticatedUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public boolean isAdmin() {
        return this.authorities != null && this.authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + RoleType.ADMIN));
    }
}
