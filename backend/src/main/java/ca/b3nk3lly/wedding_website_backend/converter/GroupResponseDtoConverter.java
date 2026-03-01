package ca.b3nk3lly.wedding_website_backend.converter;

import ca.b3nk3lly.wedding_website_backend.dto.GroupResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Group;

public class GroupResponseDtoConverter {

    public static GroupResponseDto toDto(Group group) {
        return GroupResponseDto
                .builder()
                .id(group.getId())
                .name(group.getName())
                .user(UserResponseDtoConverter.toDto(group.getUser()))
                .members(group.getMembers().stream().map(GuestResponseDtoConverter::toDto).toList())
                .build();
    }
}
