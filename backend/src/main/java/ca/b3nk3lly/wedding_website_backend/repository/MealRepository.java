package ca.b3nk3lly.wedding_website_backend.repository;

import ca.b3nk3lly.wedding_website_backend.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
}
