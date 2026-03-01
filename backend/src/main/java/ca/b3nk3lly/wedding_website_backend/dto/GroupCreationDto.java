package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

@Builder
public record GroupCreationDto(String name, String username, String password) {
}
