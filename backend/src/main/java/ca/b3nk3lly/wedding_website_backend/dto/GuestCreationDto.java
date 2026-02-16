package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

@Builder
public record GuestCreationDto(Integer groupId, String name, Boolean isAttending, Integer selectedMealId, String allergies) {
}
