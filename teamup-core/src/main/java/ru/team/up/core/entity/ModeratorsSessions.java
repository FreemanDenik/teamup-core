package ru.team.up.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MODERATORS_SESSIONS")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ModeratorsSessions {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private long moderatorId;

    private LocalDateTime lastUpdateSessionTime;

    private LocalDateTime createdSessionTime;

    private Long countOfModeratorsSessions;

  }
