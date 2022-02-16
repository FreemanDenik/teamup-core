package ru.team.up.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

/**
 * Сущность город
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CITY",
        indexes = {
        @Index(columnList = "name", name = "nameIndex"),
        @Index(columnList = "name, subject", name = "nameSubjectIndex")}
)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class City {

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название города
     */
    @Column(nullable = false, name = "name")
    private String name;

    /**
     * Название субъекта
     */

    @Column(nullable = false, name = "subject")
    private String subject;

    /**
     * Координаты (широта/latitude)
     */
    @Column(name = "lat")
    private String lat;

    /**
     * Координаты (долгота/longitude)
     */
    @Column(name = "lon")
    private String lon;

}
