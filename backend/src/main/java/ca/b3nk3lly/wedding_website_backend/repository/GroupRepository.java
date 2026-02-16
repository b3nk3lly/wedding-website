package ca.b3nk3lly.wedding_website_backend.repository;

import ca.b3nk3lly.wedding_website_backend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByUserId(Integer userId);
}
