package ca.b3nk3lly.wedding_website_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "groups")
@Entity
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_id_seq_generator")
    @SequenceGenerator(name = "guest_id_seq_generator", sequenceName = "guest_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "user_id", updatable = false)
    private Integer userId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Guest> members;
}
