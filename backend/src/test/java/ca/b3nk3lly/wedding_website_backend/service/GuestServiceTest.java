package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.dto.GuestUpdateDto;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;
import ca.b3nk3lly.wedding_website_backend.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @Captor
    ArgumentCaptor<Guest> guestCaptor;

    @Test
    void testFindById() {
        guestService.findById(1);
        Mockito.verify(guestRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void testFindByUserId() {
        guestService.findByUserId(2);
        Mockito.verify(guestRepository, Mockito.times(1)).findByUserId(2);
    }

    @Test
    void testUpdateGuest() {
        Guest existingGuest = new Guest();

        existingGuest.setId(1);
        existingGuest.setFirstName("First name");
        existingGuest.setLastName("Last name");
        existingGuest.setLastName("Last name");
        existingGuest.setIsAttending(false);
        existingGuest.setMealId(null);
        existingGuest.setAllergies("None");

        GuestUpdateDto updateDto = GuestUpdateDto.builder().isAttending(true).selectedMealId(1).allergies("Peanuts").build();

        guestService.updateOne(existingGuest, updateDto);
        Mockito.verify(guestRepository, Mockito.times(1)).save(guestCaptor.capture());
        Guest updatedGuest = guestCaptor.getValue();

        // these fields stayed the same
        assertEquals(existingGuest.getId(), updatedGuest.getId());
        assertEquals(existingGuest.getFirstName(), updatedGuest.getFirstName());
        assertEquals(existingGuest.getLastName(), updatedGuest.getLastName());

        // these fields changed
        assertEquals(existingGuest.getIsAttending(), updateDto.isAttending());
        assertEquals(existingGuest.getMealId(), updateDto.selectedMealId());
        assertEquals(existingGuest.getAllergies(), updateDto.allergies());
    }
}
