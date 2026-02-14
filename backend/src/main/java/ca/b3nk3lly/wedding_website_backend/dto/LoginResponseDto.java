package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record LoginResponseDto(String token, Date expiresAt) {
}
