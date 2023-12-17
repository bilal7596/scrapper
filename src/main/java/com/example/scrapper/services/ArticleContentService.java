package com.example.scrapper.services;

import com.example.scrapper.entity.ArticleContentEntity;
import com.example.scrapper.entity.ArticleEntity;
import com.example.scrapper.repository.ArticleContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleContentService {

    @Autowired
    private ArticleContentRepository repository;

    public List<ArticleContentEntity> getAllEntities() {
        return repository.findAll();
    }

    public Optional<ArticleContentEntity> getEntityById(Long id) {
        return repository.findById(id);
    }

    public Optional<ArticleContentEntity> getEntityByArticle(ArticleEntity articleEntity) {
        return repository.findByArticle(articleEntity);
    }

    public ArticleContentEntity saveEntity(ArticleContentEntity entity) {
        return repository.save(entity);
    }

    public void deleteEntity(Long id) {
        repository.deleteById(id);
    }
}
