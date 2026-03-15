package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.dto.GuestCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.ReservationUpdateDto;
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
    public GuestResponseDto createOne(@RequestBody GuestCreationDto creationDto) {
        return guestService.createOne(creationDto);
    }

    @PutMapping("/{guestId}")
    public GuestResponseDto updateOne(@PathVariable Integer guestId, @RequestBody String name) {
        return guestService.updateOne(guestId, name);
    }

    @DeleteMapping("/{guestId}")
    public void updateOne(@PathVariable Integer guestId) {
        guestService.deleteOne(guestId);
    }
}
