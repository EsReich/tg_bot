package com.bot.meme_generator.service;

import com.bot.meme_generator.model.Picture;
import com.bot.meme_generator.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final Long numberOfPics;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
        numberOfPics = countPictures();
    }

    @Override
    public Picture getRandomPic() {
        return pictureRepository.getPictureById(
                ThreadLocalRandom.current().nextLong(1L, numberOfPics + 1L));
    }

    @Override
    public void savePicture(Picture picture) {
       pictureRepository.savePicture(picture);
    }

    @Override
    public Long countPictures() {
        return pictureRepository.countPictures();
    }
}
