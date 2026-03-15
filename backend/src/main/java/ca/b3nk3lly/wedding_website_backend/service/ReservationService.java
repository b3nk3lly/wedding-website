package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.converter.GuestResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.GuestCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.ReservationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.entity.Group;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;
import ca.b3nk3lly.wedding_website_backend.repository.GroupRepository;
import ca.b3nk3lly.wedding_website_backend.repository.GuestRepository;
import ca.b3nk3lly.wedding_website_backend.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final GuestRepository guestRepository;
    private final GroupRepository groupRepository;

    public ReservationService(GuestRepository guestRepository, GroupRepository groupRepository) {
        this.guestRepository = guestRepository;
        this.groupRepository = groupRepository;
    }

    public GuestResponseDto createOne(GuestCreationDto dto) {
        Group group = groupRepository.getReferenceById(dto.groupId());

        Guest guest = new Guest();
        guest.setGroup(group);
        guest.setName(dto.name());

        return GuestResponseDtoConverter.toDto(guestRepository.save(guest));
    }

    public GuestResponseDto updateOne(Integer guestId, ReservationUpdateDto updateDto) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Couldn't find guest with ID " + guestId));

        if (!SecurityUtils.canUserAccessGroup(guest.getGroup())) {
            throw new AccessDeniedException("Current user cannot access the group");
        }

        guest.setName(updateDto.name());

        return GuestResponseDtoConverter.toDto(guestRepository.save(guest));
    }
}
