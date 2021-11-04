package ru.team.up.core.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность модератор
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MODERATOR_ACCOUNT")
public class Moderator extends Account{

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * количество закрытых обращений
     */
    @Column(name = "AMOUNT_OF_CLOSED_REQUESTS")
    private Long amountOfClosedRequests;

    /**
     * количество проверенных мероприятий
     */
    @Column(name = "AMOUNT_OF_CHECKED_EVENTS")
    private Long amountOfCheckedEvents;

    /**
     * количество удалённых мероприятий
     */
    @Column(name = "AMOUNT_OF_DELETED_EVENTS")
    private Long amountOfDeletedEvents;
}
