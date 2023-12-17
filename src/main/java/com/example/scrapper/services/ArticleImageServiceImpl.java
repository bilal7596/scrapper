package com.example.scrapper.services;

import com.example.scrapper.entity.ArticleImagesEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ArticleImageServiceImpl {

    List<ArticleImagesEntity> getAllEntities();

    Optional<ArticleImagesEntity> getEntityById(Long id);

    ArticleImagesEntity saveEntity(ArticleImagesEntity entity);

    void deleteEntity(Long id);

}
