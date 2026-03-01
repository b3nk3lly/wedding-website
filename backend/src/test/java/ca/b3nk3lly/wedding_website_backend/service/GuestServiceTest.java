package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.annotation.WithMockAuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestUpdateDto;
import ca.b3nk3lly.wedding_website_backend.entity.Group;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;
import ca.b3nk3lly.wedding_website_backend.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @Test
    @WithMockAuthenticatedUser(userId = 1)
    void testUpdateGuest() {
        Group existingGroup = new Group();
        existingGroup.setId(1);
        existingGroup.setUser(1);

        Guest existingGuest = new Guest();
        existingGuest.setId(1);
        existingGuest.setName("Name");
        existingGuest.setIsAttending(false);
        existingGuest.setMealId(null);
        existingGuest.setAllergies("None");
        existingGuest.setGroup(existingGroup);

        Mockito.when(guestRepository.findById(existingGuest.getId())).thenReturn(Optional.of(existingGuest));
        GuestUpdateDto updateDto = GuestUpdateDto.builder().isAttending(true).selectedMealId(1).allergies("Peanuts").build();

        GuestResponseDto responseDto = guestService.updateOne(existingGuest.getId(), updateDto);
        Mockito.verify(guestRepository, Mockito.times(1)).save(any());

        // these fields stayed the same
        assertEquals(existingGuest.getId(), responseDto.id());
        assertEquals(existingGuest.getName(), responseDto.name());

        // these fields changed
        assertEquals(updateDto.isAttending(), responseDto.isAttending());
        assertEquals(updateDto.selectedMealId(), responseDto.selectedMealId());
        assertEquals(updateDto.allergies(), responseDto.allergies());
    }
}
