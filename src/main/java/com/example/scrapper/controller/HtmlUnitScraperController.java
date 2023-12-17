package com.example.scrapper.controller;

import com.example.scrapper.services.ArticleService;
import com.example.scrapper.services.HtmlUnitScraperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/scrape")
public class HtmlUnitScraperController {

    @Autowired
    private HtmlUnitScraperService scraperService;

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public HashMap<String, Object> scrape(@RequestParam String url) {
        return scraperService.scrapeWebsite(url);
    }

    @GetMapping("/fake")
    public String fake() throws JsonProcessingException {
        List<String> urls = Arrays.asList(
                "https://www.cntraveller.com/article/how-my-rare-disability-changed-the-way-i-look-at-travel"
        );
        for (int i=0; i<urls.size(); i++) {
            System.out.println("Output Started");
            HashMap<String, Object> output = scraperService.scrapeWebsite(urls.get(i));
            System.out.println("Output" + output);
            articleService.saveArticle(output);
        }
        return "Success";
    }
}
