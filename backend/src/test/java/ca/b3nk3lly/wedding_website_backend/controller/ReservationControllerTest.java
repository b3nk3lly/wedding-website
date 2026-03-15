package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.annotation.WithMockAuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.dto.GuestCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.ReservationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.service.ReservationService;
import ca.b3nk3lly.wedding_website_backend.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @MockitoBean
    private ReservationService reservationService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockAuthenticatedUser
    void testCreateOneCallsServiceMethod() throws Exception {
        GuestCreationDto creationDto = GuestCreationDto.builder().groupId(1).selectedMealId(2).name("Name").isAttending(true).allergies("None").build();
        mockMvc.perform(post("/guests").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(creationDto))).andExpect(__ -> Mockito.verify(reservationService, Mockito.times(1)).createOne(creationDto));
    }

    @Test
    @WithMockAuthenticatedUser
    void testUpdateOneCallsServiceMethod() throws Exception {
        ReservationUpdateDto updateDto = ReservationUpdateDto.builder().selectedMealId(2).name("Name").isAttending(true).allergies("None").build();
        mockMvc.perform(put("/guests/{id}", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDto))).andExpect(__ -> Mockito.verify(reservationService, Mockito.times(1)).updateOne(1, updateDto));
    }
}
