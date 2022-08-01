package ru.team.up.input.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.dto.AdminDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDtoResponse {

   private AdminDto adminDto;

}
