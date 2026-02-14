package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

@Builder
public record MealResponseDto(Integer id, String name, String description) {
}
