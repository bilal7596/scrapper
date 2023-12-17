package com.example.scrapper.request;

import jakarta.validation.constraints.NotBlank;

public class ArticleAdd {
    @NotBlank(message = "Url is mandatory")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
