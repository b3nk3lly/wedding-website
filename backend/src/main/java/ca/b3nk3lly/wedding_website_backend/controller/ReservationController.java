package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.dto.GuestCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.ReservationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public GuestResponseDto createOne(@RequestBody GuestCreationDto creationDto) {
        return reservationService.createOne(creationDto);
    }

    @PutMapping("/{guestId}")
    public GuestResponseDto updateOne(@PathVariable Integer guestId, @RequestBody ReservationUpdateDto updateDto) {
        return reservationService.updateOne(guestId, updateDto);
    }
}
