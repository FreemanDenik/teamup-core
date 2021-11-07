package ru.team.up.core.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INTERESTS")
public class Interests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "SHORT_DESCRIPTION", nullable = false)
    private String shortDescription;

    @ManyToMany
    @JoinTable(name="USER_ACCOUNT_INTERESTS",
            joinColumns=@JoinColumn(name="INTERESTS_ID"),
            inverseJoinColumns=@JoinColumn(name="USER_ID"))
    private Set<User> users;

    @ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name="EVENT_ID")
    private Event event;
}
