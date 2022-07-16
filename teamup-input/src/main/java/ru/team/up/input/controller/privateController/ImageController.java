package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Image;
import ru.team.up.core.service.ImageService;

import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Image Private Controller", description = "Image API")
@RequestMapping(value = "/private/image")
public class ImageController {
    private ImageService imageService;


    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.saveImageForAccount(image) в теле ResponseEntity
     */
    @PostMapping("/account/{email}")
    @Operation(summary = "Добавление изображения аккаунта")
    public ResponseEntity<Image> createImageForAccount(@PathVariable @NotNull String email, @RequestBody Image image) {
        log.debug("Старт метода saveImageForAccount");
        ResponseEntity<Image>responseEntity;
        try{
            imageService.saveImageForAccount(image,email);
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        log.debug("Сформирован ответ {}", responseEntity);
        return responseEntity;
    }

    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.updateImageForAccount(image) в теле ResponseEntity
     */
    @PutMapping("/account/{email}")
    @Operation(summary = "Обновление изображения аккаунта")
    public ResponseEntity<Image> updateImageForAccount(@PathVariable @NotNull String email, @RequestBody Image image) {
        log.debug("Старт метода updateImageForAccount");
        ResponseEntity<Image>responseEntity;
        try{
            imageService.updateImageForAccount(image,email);
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        log.debug("Сформирован ответ {}", responseEntity);
        return responseEntity;
    }

    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.saveImageForEvent(image) в теле ResponseEntity
     */
    @PostMapping("/event/{eventId}")
    @Operation(summary = "Добавление изображения мероприятия")
    public ResponseEntity<Image> saveImageForEvent(@PathVariable Long eventId , @RequestBody @NotNull Image image) {
        log.debug("Старт метода добавления изображения для мероприятия");
        ResponseEntity<Image>responseEntity;
        try{
            imageService.saveImageForEvent(image,eventId);
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);
        return responseEntity;
    }
    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.saveImageForAccount(image) в теле ResponseEntity
     */
    @PutMapping("/event/{eventId}")
    @Operation(summary = "Обновление изображения мероприятия")
    public ResponseEntity<Image> updateImageForEvent(@PathVariable Long eventId , @RequestBody @NotNull Image image) {
        log.debug("Старт метода обновления изображения для мероприятия");
        ResponseEntity<Image>responseEntity;
        try{
            imageService.updateImageForEvent(image,eventId);
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);
        return responseEntity;
    }
}
