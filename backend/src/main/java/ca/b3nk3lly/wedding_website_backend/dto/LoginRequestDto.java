package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

@Builder
public record LoginRequestDto(String username, String password) {
}
