package com.example.scrapper.repository;

import com.example.scrapper.entity.ArticleContentEntity;
import com.example.scrapper.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleContentRepository extends JpaRepository<ArticleContentEntity, Long> {

    Optional<ArticleContentEntity> findByArticle(ArticleEntity articleEntity);
}
