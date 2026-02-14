package ca.b3nk3lly.wedding_website_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Table(name = "guests")
@Entity
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_id_seq_generator")
    @SequenceGenerator(name = "guest_id_seq_generator", sequenceName = "guest_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "first_name", updatable = false)
    private String firstName;

    @Column(name = "last_name", updatable = false)
    private String lastName;

    @Column(name = "attending")
    private Boolean isAttending;

    @Column(name = "mealId")
    private Integer mealId;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "user_id", updatable = false)
    private Integer userId;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}
