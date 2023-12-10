package com.example.scrapper.services;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class HtmlUnitScraperService {

    public HashMap<String, HashMap<String, String>> scrapeWebsite(String url) {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // Disable JavaScript to improve performance and avoid executing JavaScript code during scraping
            webClient.getOptions().setJavaScriptEnabled(false);
            // Load the web page
//            webClient.getOptions().setThrowExceptionOnScriptError(false);

            final HtmlPage page = webClient.getPage(url);

            StringBuilder output = new StringBuilder();
            Integer maxCount = 0;
            HashMap<String, HashMap<String, String>> parentTagAttrCount = new HashMap<String, HashMap<String, String>>();
            for (int i=0; i<page.getElementsByTagName("p").getLength(); i++) {
                String parentTag = page.getElementsByTagName("p").get(i).getParentNode().getLocalName();
                StringBuilder parentTagAttr = new StringBuilder();
                for (int y=0; y<page.getElementsByTagName("p").get(i).getParentNode().getAttributes().getLength(); y++) {
                    parentTagAttr.append(parentTag + page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getLocalName() + "='" + page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getTextContent()+"'");
                }
                HashMap<String, String> hasData = parentTagAttrCount.get(String.valueOf(parentTagAttr));
                if (hasData == null) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    Integer count = 1;
                    data.put("count", String.valueOf(count));
                    data.put("content", page.getElementsByTagName("p").get(i).getTextContent());
                    parentTagAttrCount.put(String.valueOf(parentTagAttr), data);
                    if (count > maxCount)
                        maxCount = count;
                } else {
                    String hasCount = hasData.get("count");
                    String hasContent = hasData.get("content");
                    Integer count = Integer.parseInt(hasCount) + 1;
                    hasData.put("count", String.valueOf(count));
                    hasData.put("content", hasContent.concat(page.getElementsByTagName("p").get(i).getTextContent()));
                    parentTagAttrCount.put(String.valueOf(parentTagAttr), hasData);
                    if (count > maxCount)
                        maxCount = count;
                }
//                output.append("P tag parent tag is "+parentTag+ " has attributes " + parentTagAttr + " and content is " + page.getElementsByTagName("p").get(i).getTextContent() + "\n \n \n \n");
            }
            System.out.println("maxCount" + maxCount);
            return parentTagAttrCount;
//            return String.valueOf(output);
            // Extract information from the HTML
//            return page.asNormalizedText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<String, HashMap<String, String>>();
    }
}