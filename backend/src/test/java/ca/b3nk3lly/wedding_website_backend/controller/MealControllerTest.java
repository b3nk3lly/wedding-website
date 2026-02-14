package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.annotation.WithMockAuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.domain.RoleType;
import ca.b3nk3lly.wedding_website_backend.dto.MealCreationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.dto.MealResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Meal;
import ca.b3nk3lly.wedding_website_backend.service.JwtService;
import ca.b3nk3lly.wedding_website_backend.service.MealService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MealController.class)
@EnableMethodSecurity
class MealControllerTest {

    @MockitoBean
    private MealService mealService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testIsForbidden() throws Exception {
        mockMvc.perform(get("/meals")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAuthenticatedUser(roles = RoleType.USER)
    void testCreateMealIsForbidden() throws Exception {
        MealCreationUpdateDto creationDto = MealCreationUpdateDto.builder().name("Meal Name").description("A description").build();
        mockMvc.perform(post("/meals").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(creationDto))).andExpect(status().isForbidden());
    }

    @Test
    @WithMockAuthenticatedUser(roles = RoleType.ADMIN)
    void testCreateMeal() throws Exception {
        MealCreationUpdateDto creationDto = MealCreationUpdateDto.builder().name("Meal Name").description("A description").build();

        Mockito.when(mealService.saveOne(any())).thenAnswer(invocation -> {
            Meal meal = invocation.getArgument(0);
            meal.setId(1);
            return meal;
        });

        mockMvc.perform(post("/meals").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(creationDto))).andExpect(status().isOk()).andExpect(result -> {
            MealResponseDto mealResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertThat(mealResponse.name()).isEqualTo(creationDto.name());
            assertThat(mealResponse.description()).isEqualTo(creationDto.description());
        });
    }
}
