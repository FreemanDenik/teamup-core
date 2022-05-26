package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import ru.team.up.dto.AccountDto;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IMAGE")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Image {
    /**
     * Первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Изображение в базе данных в формате бейс64
     */
    @ToStringExclude
    @Column(name = "value")
    private String value;
    /**
     * Формат изображения
     */
    @Column(name = "type")
    private String type;
    /**
     * Размер изображения
     */
    @Column(name = "size")
    private int size;
    /**
     * Качество изображения
     */
    @Column(name = "quality")
    private String quality;
    /**
     * Ширина изображения
     */
    @Column(name = "width")
    private int width;
    /**
     * Высота изображения
     */
    @Column(name = "height")
    private int height ;
    /**
     * Ссылка на изображение
     */
    @Column(name = "imageUrl")
    private String imageUrl;


    /**
     * Изображение в базе данных в формате бейс64 кодировка
     */
    public void setValue(String value) {
        this.value =  Base64.getEncoder().encodeToString(value.getBytes());
    }
}
