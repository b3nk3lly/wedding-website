package ca.b3nk3lly.wedding_website_backend.repository;

import ca.b3nk3lly.wedding_website_backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
