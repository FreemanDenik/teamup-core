package ru.team.up.sup.dto;

import lombok.Data;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.dto.SupParameterDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListSupParameterDto {
    private List<SupParameterDto<?>> list = new ArrayList<>();
    private AppModuleNameDto moduleName;

    public void addParameter(SupParameterDto<?> parameter) {
        list.add(parameter);
        moduleName = parameter.getSystemName();
    }
}
