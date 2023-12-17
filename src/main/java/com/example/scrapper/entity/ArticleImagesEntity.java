package com.example.scrapper.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "article_images")
public class ArticleImagesEntity {

    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id")
    private ArticleEntity article;

    private String imageUrl;

    public ArticleImagesEntity(ArticleEntity article, String imageUrl) {
        this.article = article;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleEntity getArticle() {
        return article;
    }

    public void setArticle(ArticleEntity article) {
        this.article = article;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
