package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

@Builder
public record GuestResponseDto(
        Integer id,
        String firstName,
        String lastName,
        Boolean isAttending,
        Integer selectedMealId,
        String allergies) {
}
