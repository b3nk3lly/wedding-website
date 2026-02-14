package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.annotation.WithMockAuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.converter.GuestResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.GuestResponseDto;
import ca.b3nk3lly.wedding_website_backend.dto.GuestUpdateDto;
import ca.b3nk3lly.wedding_website_backend.entity.Guest;
import ca.b3nk3lly.wedding_website_backend.service.GuestService;
import ca.b3nk3lly.wedding_website_backend.service.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    @MockitoBean
    private GuestService guestService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testIsForbidden() throws Exception {
        mockMvc.perform(get("/guests")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAuthenticatedUser(userId = 2)
    void testGetGuestsForUser() throws Exception {
        Guest guest1 = new Guest();
        guest1.setId(1);
        guest1.setUserId(2);
        guest1.setFirstName("Test");
        guest1.setLastName("User");

        Mockito.when(guestService.findByUserId(2)).thenReturn(List.of(guest1));

        List<GuestResponseDto> expectedGuestsResponse = List.of(GuestResponseDtoConverter.toDto(guest1));

        mockMvc.perform(get("/guests")).andExpect(status().isOk()).andExpect(result -> {
            List<GuestResponseDto> guestsResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertThat(guestsResponse).isEqualTo(expectedGuestsResponse);
        });
    }

    @Test
    @WithMockAuthenticatedUser(userId = 2)
    void testUpdateUser() throws Exception {
        Guest guest1 = new Guest();
        guest1.setId(1);
        guest1.setUserId(2);
        guest1.setFirstName("Test");
        guest1.setLastName("User");

        GuestUpdateDto updateDto = GuestUpdateDto.builder().isAttending(true).selectedMealId(1).allergies("Codeine").build();

        Guest expectedUpdatedGuest = new Guest();
        expectedUpdatedGuest.setIsAttending(true);
        expectedUpdatedGuest.setMealId(1);
        expectedUpdatedGuest.setAllergies("Codeine");

        Mockito.when(guestService.findById(guest1.getId())).thenReturn(Optional.of(guest1));
        Mockito.when(guestService.updateOne(guest1, updateDto)).thenReturn(expectedUpdatedGuest);


        mockMvc.perform(put("/guests/{id}", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDto))).andExpect(status().isOk()).andExpect(result -> {
            GuestResponseDto guestsResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertThat(guestsResponse).isEqualTo(GuestResponseDtoConverter.toDto(expectedUpdatedGuest));
        });
    }
}
