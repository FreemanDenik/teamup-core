package ru.team.up.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACCOUNT_CREATED_TIME", nullable = false)
    private String accountCreatedTime;

    @Column(name = "LAST_ACCOUNT_ACTIVITY", nullable = false)
    private String lastAccountActivity;
}
