package com.example.scrapper.services;

import com.example.scrapper.entity.ArticleEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<ArticleEntity> getAllEntities();

    Optional<ArticleEntity> getEntityById(Long id);

    ArticleEntity saveEntity(ArticleEntity entity);

    void deleteEntity(Long id);

    ArticleEntity saveArticle(HashMap<String, Object> record) throws JsonProcessingException;
}
