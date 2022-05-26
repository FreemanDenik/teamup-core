package ru.team.up.core.service;

import ru.team.up.core.entity.Image;

public interface ImageService {

    void saveImageForAccount(Image image, String email);

    void updateImageForAccount(Image image, String email);

    void saveImageForEvent(Image image, Long eventId);

    void updateImageForEvent(Image image, Long eventId);


}
