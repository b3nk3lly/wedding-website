package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.converter.GuestResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.domain.AuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestUpdateDto;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;
import ca.b3nk3lly.wedding_website_backend.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<GuestResponseDto> getGuestsForUser(@AuthenticationPrincipal AuthenticatedUser user) {
        return guestService.findByUserId(user.getId())
                .stream()
                .map(GuestResponseDtoConverter::toDto)
                .toList();
    }

    @PutMapping("/{guestId}")
    public GuestResponseDto updateGuest(
            @PathVariable Integer guestId,
            @RequestBody GuestUpdateDto updateDto,
            @AuthenticationPrincipal AuthenticatedUser authUser) {

        Guest guest = guestService.findById(guestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find guest with ID" + guestId));

        if (!guest.getUserId().equals(authUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot access guest with ID " + guestId);
        }

        Guest updatedGuest = guestService.updateOne(guest, updateDto);
        return GuestResponseDtoConverter.toDto(updatedGuest);
    }
}
