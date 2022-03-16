
package ru.team.up.input.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.dto.InterestsDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterestsDtoResponse {

    private InterestsDto interestsDto;
}