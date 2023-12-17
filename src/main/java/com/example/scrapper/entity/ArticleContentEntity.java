package com.example.scrapper.entity;


import jakarta.persistence.*;

@Entity
@Table(name="article_contents")
public class ArticleContentEntity {

    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name="article_id")
    private ArticleEntity article;

    @Column(columnDefinition = "TEXT")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
