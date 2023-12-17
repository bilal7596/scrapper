package com.example.scrapper.component;

import com.example.scrapper.repository.ScrapperRepository;
import com.example.scrapper.services.ArticleService;
import com.example.scrapper.services.HtmlUnitScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

//    @Autowired
    private ScrapperRepository scrapperRepository;

    @Autowired
    private HtmlUnitScraperService scrapperService;

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {


        System.out.println("Hiiiiiiii------------");


        // Add dummy data here
//        YourEntity entity1 = new YourEntity();
//        entity1.setColumnName("Dummy Data 1");
//        yourEntityRepository.save(entity1);
//
//        YourEntity entity2 = new YourEntity();
//        entity2.setColumnName("Dummy Data 2");
//        yourEntityRepository.save(entity2);

        // Add more dummy data as needed
    }
}
