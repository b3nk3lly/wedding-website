package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.dto.GuestCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestUpdateDto;
import ca.b3nk3lly.wedding_website_backend.service.GuestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public GuestResponseDto createGuest(@RequestBody GuestCreationDto creationDto) {
        return guestService.createOne(creationDto);
    }

    @PutMapping("/{guestId}")
    public GuestResponseDto updateGuest(@PathVariable Integer guestId, @RequestBody GuestUpdateDto updateDto) {
        return guestService.updateOne(guestId, updateDto);
    }
}
