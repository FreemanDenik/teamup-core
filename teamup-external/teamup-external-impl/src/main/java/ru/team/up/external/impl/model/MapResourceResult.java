package ru.team.up.external.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
 * Сущность геокодированной адресной и геометрической информации
 * */

@Getter
@Setter
@NoArgsConstructor
public class MapResourceResult {

    /*
    * Массив, содержащий отдельные компоненты, применимые к этому адресу
    * */
    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    /*
    * Строка, содержащая удобочитаемый адрес этого места
    * */
    @JsonProperty("formatted_address")
    public String formattedAddress;

    /*
    * Уникальный идентификатор, который можно использовать с другими API Google
    * */
    @JsonProperty("place_id")
    public String placeId;

    /*
    * Закодированная ссылка на местоположение, полученная из координат широты и долготы
    * */
    @JsonProperty("plus_code")
    public PlusCode plusCode;

    /*
    *  Массив, обозначающий все населенные пункты, содержащиеся в почтовом индексе
    * */
    @JsonProperty("postcode_localities")
    public List<String> postcodeLocalities;

    /*
    * Массив - тип адреса
    * */
    public List<String> types;

    /*
    * Геометрическая информация о месте
    * */
    public Geometry geometry;

}