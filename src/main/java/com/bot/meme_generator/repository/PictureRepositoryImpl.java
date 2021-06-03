package com.bot.meme_generator.repository;

import com.bot.meme_generator.model.Picture;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class PictureRepositoryImpl implements PictureRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PictureRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Picture getPictureById(Long id) {
        return entityManager.find(Picture.class, id);
    }

    @Override
    public void savePicture(Picture picture) {
        entityManager.persist(picture);
    }

    @Override
    public Long countPictures() {
        return (Long) entityManager.createQuery("select count(*) from Picture").getSingleResult();
    }
}
