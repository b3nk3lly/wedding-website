package ca.b3nk3lly.wedding_website_backend.converter;

import ca.b3nk3lly.wedding_website_backend.dto.UserResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.User;

public class UserResponseDtoConverter {

    public static UserResponseDto toDto(User user) {
        return UserResponseDto
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
