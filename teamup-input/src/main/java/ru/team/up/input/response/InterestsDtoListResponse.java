package ru.team.up.input.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.dto.InterestsDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterestsDtoListResponse {

    private List<InterestsDto> interestsDtoList;
}
