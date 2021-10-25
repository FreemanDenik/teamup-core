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
@Table(name = "users")
public class User extends Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "aboutUser")
    private String aboutUser;

    @ManyToMany (cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
    @JoinTable (name="user_interests", joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="interests_id"))
    @Column(name = "userInterests")
    private Set<Interests> userInterests;
}
