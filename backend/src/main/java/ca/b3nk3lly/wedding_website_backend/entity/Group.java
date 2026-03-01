package ca.b3nk3lly.wedding_website_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "groups")
@Entity
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_seq_generator")
    @SequenceGenerator(name = "group_id_seq_generator", sequenceName = "group_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Guest> members = new ArrayList<>();
}
