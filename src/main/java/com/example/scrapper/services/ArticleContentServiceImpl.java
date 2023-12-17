package com.example.scrapper.services;

import com.example.scrapper.entity.ArticleContentEntity;
import com.example.scrapper.entity.ArticleEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ArticleContentServiceImpl {

    List<ArticleContentEntity> getAllEntities();

    Optional<ArticleContentEntity> getEntityById(Long id);

    ArticleContentEntity saveEntity(ArticleContentEntity entity);

    void deleteEntity(Long id);

}
