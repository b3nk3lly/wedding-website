package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.dto.GuestUpdateDto;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;
import ca.b3nk3lly.wedding_website_backend.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Optional<Guest> findById(Integer id) {
        return guestRepository.findById(id);
    }

    public List<Guest> findByUserId(Integer userId) {
        return guestRepository.findByUserId(userId);
    }

    public Guest updateOne(Guest guest, GuestUpdateDto updateDto) {
        guest.setIsAttending(updateDto.isAttending());
        guest.setMealId(updateDto.selectedMealId());
        guest.setAllergies(updateDto.allergies());

        return guestRepository.save(guest);
    }
}
