package ca.b3nk3lly.wedding_website_backend.entity;

import ca.b3nk3lly.wedding_website_backend.domain.AttendanceSelection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "reservations")
@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_id_seq_generator")
    @SequenceGenerator(name = "reservation_id_seq_generator", sequenceName = "reservation_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "attending")
    private AttendanceSelection isAttending;

    @Column(name = "mealId")
    private Integer mealId;

    @Column(name = "has_allergies")
    private Boolean hasAllergies;

    @Column(name = "allergies")
    private String allergies;
}
