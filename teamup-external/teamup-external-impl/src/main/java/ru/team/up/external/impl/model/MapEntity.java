package ru.team.up.external.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/*
* Сущность, содержащая ответ от GoogleMapApi
* */

@Getter
@Setter
@NoArgsConstructor
public class MapEntity {

    /*
    * Массив геокодированной адресной и геометрической информации
    * */
    @JsonProperty("results")
    public ArrayList<MapResourceResult> mapResourceResults;

    /*
     *  Закодированная ссылка на местоположение, полученная из координат широты и долготы
     * */
    @JsonProperty("plus_code")
    private PlusCode plusCode;

    /*
    * Состояние запроса
    * */
    private String status;

}