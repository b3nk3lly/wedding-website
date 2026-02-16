package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.converter.MealResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.MealCreationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.dto.MealResponseDto;
import ca.b3nk3lly.wedding_website_backend.service.MealService;
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
    public MealResponseDto createOne(@RequestBody MealCreationUpdateDto dto) {
        return mealService.saveOne(dto);
    }
}
