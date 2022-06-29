package ru.team.up.moderator.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.payload.Payload;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignedEventPayload implements Payload {
    private Moderator moderator;

    private Event event;
}
