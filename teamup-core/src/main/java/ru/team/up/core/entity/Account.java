package ru.team.up.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "eMail", nullable = false)
    private String eMail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "accountCreatedTime", nullable = false)
    private String accountCreatedTime;

    @Column(name = "lastAccountActivity", nullable = false)
    private String lastAccountActivity;
}
