package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.converter.MealResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.MealCreationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.dto.MealResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Meal;
import ca.b3nk3lly.wedding_website_backend.service.MealService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<MealResponseDto> getMeals() {
        return mealService.findAll()
                .stream()
                .map(MealResponseDtoConverter::toDto)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MealResponseDto createOne(@RequestBody MealCreationUpdateDto dto) {
        Meal newMeal = new Meal();
        newMeal.setName(dto.name());
        newMeal.setDescription(dto.description());

        newMeal = mealService.saveOne(newMeal);

        return MealResponseDtoConverter.toDto(newMeal);
    }
}
