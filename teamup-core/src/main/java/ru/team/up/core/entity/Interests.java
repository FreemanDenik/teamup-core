package ru.team.up.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "interests")
public class Interests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "shortDescription", nullable = false)
    private String shortDescription;

    @ManyToMany
    @JoinTable(name="user_interests",
            joinColumns=@JoinColumn(name="interests_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private Set<User> users;
}
