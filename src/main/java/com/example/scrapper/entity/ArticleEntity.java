package com.example.scrapper.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;

@Entity
@Table(name="articles")
public class ArticleEntity {
    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Boolean isArticle;

    private String url;

    private String topImageUrl;

    @ColumnTransformer(
            read = "additional_details::json",
            write = "?::jsonb"
    )
    @Column(columnDefinition = "jsonb")
    private String additionalDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsArticle() {
        return isArticle;
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleContentEntity> articleContent;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleImagesEntity> articleImages;

    public void setIsArticle(Boolean article) {
        isArticle = article;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopImageUrl() {
        return topImageUrl;
    }

    public void setTopImageUrl(String topImageUrl) {
        this.topImageUrl = topImageUrl;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public List<ArticleImagesEntity> getArticleImages() {
        return articleImages;
    }

    public void setArticleImages(List<ArticleImagesEntity> articleImages) {
        this.articleImages = articleImages;
    }
}
