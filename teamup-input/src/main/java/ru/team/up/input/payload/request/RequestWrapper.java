package ru.team.up.input.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.core.entity.Application;
import ru.team.up.core.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestWrapper {

    User user;
    Application application;

}
