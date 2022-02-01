package ru.team.up.external.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* Сущность - геометрическая информация о месте
* */

@Getter
@Setter
@NoArgsConstructor
public class Geometry {

    /*
    * Ограничивающая рамка, которая может полностью содержать возвращаемый результат. (необязательно возвращается)
    * */
    private Bounds bounds;

    /*
    * Геокодированные значения широты и долготы
    * */
    public Location location;

    /*
    * Рекомендуемая область просмотра для отображения возвращенного результата.
    * Обычно область просмотра используется для кадрирования результата при его отображении пользователю
    * */
    private Viewport viewport;

    /*
    * Дополнительные данные об указанном месте
    * */
    @JsonProperty("location_type")
    private String locationType;

}