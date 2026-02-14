package ca.b3nk3lly.wedding_website_backend.converter;

import ca.b3nk3lly.wedding_website_backend.dto.MealResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Meal;

public class MealResponseDtoConverter {

    public static MealResponseDto toDto(Meal meal) {
        return MealResponseDto
                .builder()
                .id(meal.getId())
                .name(meal.getName())
                .description(meal.getDescription())
                .build();
    }
}
