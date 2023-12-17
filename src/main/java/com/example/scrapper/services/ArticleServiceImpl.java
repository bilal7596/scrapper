package com.example.scrapper.services;
import com.example.scrapper.entity.ArticleContentEntity;
import com.example.scrapper.entity.ArticleEntity;
import com.example.scrapper.entity.ArticleImagesEntity;
import com.example.scrapper.repository.ArticleContentRepository;
import com.example.scrapper.repository.ArticleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private ArticleContentService articleContentService;

    @Autowired
    private ArticleImageService articleImageService;

    public List<ArticleEntity> getAllEntities() {
        return repository.findAll();
    }

    public Optional<ArticleEntity> getEntityById(Long id) {
        return repository.findById(id);
    }

    public ArticleEntity saveEntity(ArticleEntity entity) {
        return repository.save(entity);
    }

    public void deleteEntity(Long id) {
        repository.deleteById(id);
    }

    public ArticleEntity saveArticle(HashMap<String, Object> output) throws JsonProcessingException {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(output.get("title").toString());
        articleEntity.setIsArticle(true);
        articleEntity.setUrl(output.get("url").toString());
        articleEntity.setTopImageUrl(output.get("topImageUrl") != null ? output.get("topImageUrl").toString() : null);
        ObjectMapper om = new ObjectMapper();
        articleEntity.setAdditionalDetails(om.writeValueAsString(output.get("additionalDetails")));
        ArticleEntity savedEntity = saveEntity(articleEntity);
        System.out.println("savedEntity"+ savedEntity);

        ArticleContentEntity articleContent = new ArticleContentEntity();
        articleContent.setContent(output.get("content").toString());
        articleContent.setArticle(savedEntity);
        articleContentService.saveEntity(articleContent);

        List<String> images = (List<String>) output.get("images");
        List<ArticleImagesEntity> articleImagesEntities = new ArrayList<>();
        for (String image : images) {
            articleImagesEntities.add(new ArticleImagesEntity(savedEntity, image));
        }
        articleImageService.saveAllEntity(articleImagesEntities);

        return savedEntity;
    }
}

