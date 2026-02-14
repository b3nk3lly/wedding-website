package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

@Builder
public record GuestUpdateDto(Boolean isAttending, Integer selectedMealId, String allergies) {
}
