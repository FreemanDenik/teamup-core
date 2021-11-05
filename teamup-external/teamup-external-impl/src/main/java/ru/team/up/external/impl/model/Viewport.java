package ru.team.up.external.impl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* Сущность - Рекомендуемая область просмотра для отображения возвращенного результата
* */

@Getter
@Setter
@NoArgsConstructor
public class Viewport {

    /*
     * северо-восток
     * */
    public Northeast northeast;

    /*
     * юго-запад
     * */
    public Southwest southwest;
}