package ru.team.up.input.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Pavel Kondrashov on 27.10.2021
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEventResponse {
    private String message;
    private String status;
}
