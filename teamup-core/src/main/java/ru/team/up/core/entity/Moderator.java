package ru.team.up.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MODERATOR_ACCOUNT")
public class Moderator extends Account{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(name = "AMOUNT_OF_CLOSED_REQUESTS")
    private Long amountOfClosedRequests;

    @Column(name = "AMOUNT_OF_CHECKED_EVENTS")
    private Long amountOfCheckedEvents;

    @Column(name = "AMOUNT_OF_DELETED_EVENTS")
    private Long amountOfDeletedEvents;
}
