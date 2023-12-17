package com.example.scrapper.controller;


import com.example.scrapper.entity.ArticleEntity;
import com.example.scrapper.request.ArticleAdd;
import com.example.scrapper.services.ArticleContentService;
import com.example.scrapper.services.ArticleService;
import com.example.scrapper.services.HtmlUnitScraperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @Autowired
    HtmlUnitScraperService scraperService;

    @GetMapping
    public List<ArticleEntity> getAllEntities() {
        return service.getAllEntities();
    }

    @GetMapping("/{id}")
    public Optional<ArticleEntity> getEntityById(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @PostMapping
    public ArticleEntity saveEntity(@RequestBody ArticleEntity entity) {
        return service.saveEntity(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @PostMapping("/save")
    public ArticleEntity saveArticle(@Valid @RequestBody ArticleAdd articleAddReq) throws JsonProcessingException {
        HashMap<String, Object> output = scraperService.scrapeWebsite(articleAddReq.getUrl());
        return service.saveArticle(output);
    }

}
