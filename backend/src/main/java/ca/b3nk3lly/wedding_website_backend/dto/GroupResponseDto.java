package ca.b3nk3lly.wedding_website_backend.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupResponseDto(Integer id, String name, List<GuestResponseDto> members) {
}
