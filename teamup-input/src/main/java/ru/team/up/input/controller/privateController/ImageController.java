package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return Результат работы метода imageService.saveImageForAccount(image,email)
     */
    @PostMapping("/account/{email}")
    @Operation(summary = "Добавление изображения аккаунта")
    public Image createImageForAccount(@PathVariable @NotNull String email, @RequestBody Image image) {
        log.debug("Старт метода saveImageForAccount");
        try{
            imageService.saveImageForAccount(image,email);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            throw new RuntimeException("Ошибка при сохранении изображения");
        }
        log.debug("Изображение к аккаунту добавлено");
        return image;
    }

    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.updateImageForAccount(image,email)
     */
    @PutMapping("/account/{email}")
    @Operation(summary = "Обновление изображения аккаунта")
    public Image updateImageForAccount(@PathVariable @NotNull String email, @RequestBody Image image) {
        log.debug("Старт метода updateImageForAccount");
        try{
            imageService.updateImageForAccount(image,email);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            throw new RuntimeException("Ошибка при обновлении изображения");
        }
        log.debug("Изображение у аккаунта обновлено");
        return image;
    }

    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.saveImageForEvent(image,eventId)
     */
    @PostMapping("/event/{eventId}")
    @Operation(summary = "Добавление изображения мероприятия")
    public Image saveImageForEvent(@PathVariable Long eventId , @RequestBody @NotNull Image image) {
        log.debug("Старт метода добавления изображения для мероприятия");
        try{
            imageService.saveImageForEvent(image,eventId);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            throw new RuntimeException("Ошибка при добавлении изображения");
        }
        log.debug("Изображение к мероприятию добавлено");
        return image;
    }

    /**
     * @param image Изображение добавляемое в аккаунт
     * @return Результат работы метода imageService.saveImageForAccount(image,eventId)
     */
    @PutMapping("/event/{eventId}")
    @Operation(summary = "Обновление изображения мероприятия")
    public Image updateImageForEvent(@PathVariable Long eventId , @RequestBody @NotNull Image image) {
        log.debug("Старт метода обновления изображения для мероприятия");
        try{
            imageService.updateImageForEvent(image,eventId);
        } catch (PersistenceException e){
            log.debug(e.getMessage());
            throw new RuntimeException("Ошибка при обновлении изображения");
        }
        log.debug("Изображение у мероприятия обновлено");
        return image;
    }
}
