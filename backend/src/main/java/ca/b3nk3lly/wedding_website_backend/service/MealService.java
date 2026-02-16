package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.converter.MealResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.MealCreationUpdateDto;
import ca.b3nk3lly.wedding_website_backend.dto.MealResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Meal;
import ca.b3nk3lly.wedding_website_backend.repository.MealRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MealResponseDto saveOne(MealCreationUpdateDto dto) {
        Meal newMeal = new Meal();
        newMeal.setName(dto.name());
        newMeal.setDescription(dto.description());

        return MealResponseDtoConverter.toDto(mealRepository.save(newMeal));
    }
}
