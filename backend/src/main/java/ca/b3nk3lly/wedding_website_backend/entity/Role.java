package ca.b3nk3lly.wedding_website_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "roles")
@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq_generator")
    @SequenceGenerator(name = "role_id_seq_generator", sequenceName = "role_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;
}
