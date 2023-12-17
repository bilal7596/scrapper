package com.example.scrapper.repository;

import com.example.scrapper.entity.ArticleEntity;
import com.example.scrapper.entity.ArticleImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImagesEntity, Long> {
}
