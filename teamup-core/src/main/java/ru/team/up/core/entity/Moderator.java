package ru.team.up.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "moderators")
public class Moderator extends Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amountOfClosedRequests")
    private Long amountOfClosedRequests;

    @Column(name = "amountOfCheckedEvents")
    private Long amountOfCheckedEvents;

    @Column(name = "amountOfDeletedEvents")
    private Long amountOfDeletedEvents;
}
