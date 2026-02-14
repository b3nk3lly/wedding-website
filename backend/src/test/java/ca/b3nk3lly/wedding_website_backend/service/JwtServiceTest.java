package ca.b3nk3lly.wedding_website_backend.service;


import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = JwtService.class)
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testGeneratedTokenContainsCorrectClaims() {
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "abc123";
            }

            @Override
            public String getUsername() {
                return "brkelly";
            }
        };

        String token = jwtService.generateToken(userDetails);

        Claims claims = jwtService.extractAllClaims(token);

        assertEquals(userDetails.getUsername(), claims.getSubject());
        assertThat(claims.getIssuedAt()).isCloseTo(Instant.now(), 5000);
        assertThat(claims.getExpiration()).isCloseTo(Instant.now().plus(7, ChronoUnit.DAYS), 5000);
    }
}
