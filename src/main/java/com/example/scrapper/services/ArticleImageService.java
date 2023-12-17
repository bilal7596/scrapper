package com.example.scrapper.services;

import com.example.scrapper.entity.ArticleContentEntity;
import com.example.scrapper.entity.ArticleEntity;
import com.example.scrapper.entity.ArticleImagesEntity;
import com.example.scrapper.repository.ArticleContentRepository;
import com.example.scrapper.repository.ArticleImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleImageService {

    @Autowired
    private ArticleImageRepository repository;

    public List<ArticleImagesEntity> getAllEntities() {
        return repository.findAll();
    }

    public Optional<ArticleImagesEntity> getEntityById(Long id) {
        return repository.findById(id);
    }

    public ArticleImagesEntity saveEntity(ArticleImagesEntity entity) {
        return repository.save(entity);
    }

    public void deleteEntity(Long id) {
        repository.deleteById(id);
    }

    public List<ArticleImagesEntity> saveAllEntity(List<ArticleImagesEntity> entities) {
        return repository.saveAll(entities);
    }
}
