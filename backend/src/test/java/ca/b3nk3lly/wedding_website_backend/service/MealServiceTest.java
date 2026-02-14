package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.entity.Meal;
import ca.b3nk3lly.wedding_website_backend.repository.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    @Captor
    private ArgumentCaptor<Meal> mealCaptor;

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
        Meal meal = new Meal();
        meal.setId(1);
        meal.setName("Meal 1");
        meal.setDescription("Description 1");

        mealService.saveOne(meal);

        Mockito.verify(mealRepository, Mockito.times(1)).save(mealCaptor.capture());

        Meal savedMeal = mealCaptor.getValue();

        assertEquals(meal, savedMeal);
    }
}
