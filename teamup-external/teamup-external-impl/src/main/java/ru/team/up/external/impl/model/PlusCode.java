package ru.team.up.external.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* Сущность - закодированная ссылка на местоположение, полученная из координат широты и долготы
* */

@Getter
@Setter
@NoArgsConstructor
public class PlusCode {

    /*
    *  Местный код из 6 или более символов с явным местоположением
    * */
    @JsonProperty("compound_code")
    private String compoundCode;

    /*
    *  Четырехзначный код города и шестизначный или более длинный местный код
    * */
    @JsonProperty("global_code")
    private String globalCode;
}