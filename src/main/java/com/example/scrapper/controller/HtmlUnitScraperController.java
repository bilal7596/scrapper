package com.example.scrapper.controller;

import com.example.scrapper.services.HtmlUnitScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/scrape")
public class HtmlUnitScraperController {

    @Autowired
    private HtmlUnitScraperService scraperService;

    @GetMapping
    public HashMap<String, HashMap<String, String>> scrape(@RequestParam String url) {
        return scraperService.scrapeWebsite(url);
//        return new HashMap<String, Integer>();
    }
}
