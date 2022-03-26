package ru.team.up.sup.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.team.up.dto.SupParameterDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Data
public class SupRepository {
    private Map<String, SupParameterDto<?>> supParameterDtoMap = new HashMap<>();

    public void add(SupParameterDto<?> supParameterDto) {
        String parameterName = supParameterDto.getParameterName();
        //Проверка есть ли такой параметр в Map
        if (!supParameterDtoMap.containsKey(parameterName)) {
            //Вставка параметра, которого нет в таблице
            supParameterDtoMap.put(parameterName, supParameterDto);
        } else {
            SupParameterDto<?> oldParam = supParameterDtoMap.get(parameterName);
            //Проверка какой из параметров последний раз обновлен и его сохранение
            if (oldParam.getUpdateTime().isBefore(supParameterDto.getUpdateTime())) {
                supParameterDtoMap.put(parameterName, supParameterDto);
            }
        }

    }

    public SupParameterDto<?> getOne(String paramName) {
        return supParameterDtoMap.get(paramName);
    }

    public List<SupParameterDto<?>> findAll() {
        ArrayList<SupParameterDto<?>> supParameterList = new ArrayList<>(supParameterDtoMap.values());
        return supParameterList;
    }
}