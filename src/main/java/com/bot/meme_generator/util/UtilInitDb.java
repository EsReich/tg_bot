package com.bot.meme_generator.util;

import com.bot.meme_generator.model.Picture;
import com.bot.meme_generator.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Profile("dev")
@ConditionalOnProperty(name = "spring.jpa.hibernate.ddl-auto", havingValue = "create")
public class UtilInitDb {

    private final PictureService pictureService;

    @Autowired
    public UtilInitDb(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostConstruct
    public void initDatabase() {
        try {
            List<File> pics = Arrays.asList(
                    Objects.requireNonNull(new File(Objects.requireNonNull(getClass()
                    .getClassLoader()
                    .getResource("static/memes"))
                    .toURI()).listFiles()));

            pics.stream().forEach(file -> {
                Picture pic = new Picture();
                pic.setName(file.getName());
                pictureService.savePicture(pic);
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
