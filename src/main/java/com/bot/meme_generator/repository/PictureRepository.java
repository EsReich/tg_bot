package com.bot.meme_generator.repository;

import com.bot.meme_generator.model.Picture;

public interface PictureRepository {
    Picture getPictureById(Long id);
    void savePicture(Picture picture);
    Long countPictures();
}
