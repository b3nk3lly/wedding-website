package ca.b3nk3lly.wedding_website_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Table(name = "guests")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_id_seq_generator")
    @SequenceGenerator(name = "guest_id_seq_generator", sequenceName = "guest_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", updatable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "group_id", updatable = false, nullable = false)
    private Group group;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;
}
