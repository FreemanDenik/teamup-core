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
@Table(name = "USER_ACCOUNT")
public class User extends Account{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @Column(name = "ABOUT_USER")
    private String aboutUser;

    @ManyToMany (cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable (name="USER_ACCOUNT_INTERESTS", joinColumns=@JoinColumn (name="USER_ID"),
            inverseJoinColumns=@JoinColumn(name="INTERESTS_ID"))
    @Column(name = "USER_INTERESTS")
    private Set<Interests> userInterests;
}
