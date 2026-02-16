package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.dto.MealCreationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.dto.MealResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Meal;
import ca.b3nk3lly.wedding_website_backend.repository.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    @Test
    void testFindAll() {
        Meal meal1 = new Meal();
        meal1.setId(1);
        meal1.setName("Meal 1");
        meal1.setDescription("Description 1");

        Meal meal2 = new Meal();
        meal2.setId(2);
        meal2.setName("Meal 2");
        meal2.setDescription("Description 2");

        List<Meal> expectedMeals = List.of(meal1, meal2);

        Mockito.when(mealRepository.findAll()).thenReturn(expectedMeals);

        List<Meal> actualMeals = mealService.findAll();

        assertEquals(expectedMeals, actualMeals);
    }

    @Test
    void testSaveOne() {
        MealCreationUpdateDto creationDto = MealCreationUpdateDto.builder().name("My Meal").description("My Description").build();

        MealResponseDto responseDto = mealService.saveOne(creationDto);

        Mockito.verify(mealRepository, Mockito.times(1)).save(any());

        assertEquals(creationDto.name(), responseDto.name());
        assertEquals(creationDto.description(), responseDto.description());
    }
}
