package com.bot.meme_generator.service;

import com.bot.meme_generator.model.Picture;

public interface PictureService {
    Picture getRandomPic();
    void savePicture(Picture picture);
    Long countPictures();
}
