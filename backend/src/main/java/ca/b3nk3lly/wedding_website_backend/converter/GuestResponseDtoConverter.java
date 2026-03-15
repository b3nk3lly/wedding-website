package ca.b3nk3lly.wedding_website_backend.converter;

import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;

public class GuestResponseDtoConverter {

    public static GuestResponseDto toDto(Guest guest) {
        return GuestResponseDto
                .builder()
                .id(guest.getId())
                .name(guest.getName())
                .build();
    }
}
