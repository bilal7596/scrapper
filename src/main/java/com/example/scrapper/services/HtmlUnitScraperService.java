package com.example.scrapper.services;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class HtmlUnitScraperService {

    public boolean isArrayContains(String value) {
        String[] array = { "p", "pre", "h1", "h2", "h3", "h4", "h5", "h6", "code", "a", "span", "div", "em", "figure" };
        return Arrays.stream(array).anyMatch(value::equals);
    }

    public StringBuilder iterator(DomElement domParentChild, StringBuilder output, String openTagName, String closeTagName, List<String> images) {
        if (domParentChild.getChildElementCount() > 0) {
            Iterator<DomElement> domChildren1 = domParentChild.getChildElements().iterator();

            StringBuilder op = new StringBuilder();
            while (domChildren1.hasNext()) {
                DomElement dom = domChildren1.next();

                if (dom.getPreviousSibling() != null && dom.getPreviousSibling().getLocalName() == null)
                    op.append(dom.getPreviousSibling().getTextContent());

                if(dom.getChildElementCount() > 0) {
                    this.iterator(dom, op, openTagName, closeTagName, images);
                } else {
                    if (isArrayContains(dom.getTagName())) {
                        op.append("<" + dom.getTagName() + ">" + dom.getTextContent() + "</" + dom.getTagName() + ">");
                    } else if (dom.getTagName().equals("img")) {
                        images.add(dom.getAttribute("src"));
                        op.append("<img id='pocket-image-'"+images.size()+1+"'/>");
                    } else
                        System.out.println("not allowed1"+ dom.getTagName());
                }

                if (!domChildren1.hasNext()) {
                    if (dom.getNextSibling() != null)
                        op.append(dom.getNextSibling().getTextContent());
                    if (isArrayContains(domParentChild.getTagName()))
                        output.append("<"+domParentChild.getTagName()+">"+op+"</"+domParentChild.getTagName()+">");
                    else
                        System.out.println("not allowed2"+ dom.getTagName());

                }
            }


//            for (DomElement dom : domChildren1) {
//                if (dom.getPreviousSibling() != null) {
//                    output.append(dom.getPreviousSibling());
//                    openTagName = "";
//                }
//                openTagName = openTagName + "<"+dom.getTagName()+">";
//                closeTagName = "</"+dom.getTagName()+">"+closeTagName;
//                if (dom.getChildElementCount() > 0) {
//                    this.iterator(dom, output, openTagName, closeTagName);
//                } else {
//                    output.append(openTagName + dom.getTextContent() + closeTagName);
//                }
//            }
        } else {
            output.append(openTagName + domParentChild.getTextContent() + closeTagName);
        }
        return output;
    }

    public HashMap<String, Object> scrapeWebsite(String url) {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // Disable JavaScript to improve performance and avoid executing JavaScript code during scraping
            webClient.getOptions().setJavaScriptEnabled(false);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);

            final HtmlPage page = webClient.getPage(url);

            Integer maxCount = 0;
            String finalTag = null;
            String finalClass = null;
            HashMap<String, HashMap<String, String>> parentTagAttrCount = new HashMap<String, HashMap<String, String>>();

            for (int i=0; i<page.getElementsByTagName("p").getLength(); i++) {
                String parentTag = page.getElementsByTagName("p").get(i).getParentNode().getLocalName();
                StringBuilder parentTagAttr = new StringBuilder();
                String parentClassAttr = null;
                for (int y=0; y<page.getElementsByTagName("p").get(i).getParentNode().getAttributes().getLength(); y++) {
//                    parentClassAttr = page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getTextContent();
                    if (page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getLocalName().toString().equals("class"))
                        parentClassAttr = page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getTextContent();

//                    System.out.println(page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getLocalName() + "='" + page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getTextContent());
                    parentTagAttr.append(parentTag + page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getLocalName() + "='" + page.getElementsByTagName("p").get(i).getParentNode().getAttributes().item(y).getTextContent()+"'");
                }
                HashMap<String, String> hasData = parentTagAttrCount.get(String.valueOf(parentTagAttr));
                if (hasData == null) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    Integer count = 1;
                    data.put("count", String.valueOf(count));
                    data.put("content", page.getElementsByTagName("p").get(i).getTextContent());
                    parentTagAttrCount.put(String.valueOf(parentTagAttr), data);
                    if (count > maxCount) {
                        finalTag = String.valueOf(parentTag);
                        finalClass = String.valueOf(parentClassAttr);
                        maxCount = count;
                    }
                } else {
                    String hasCount = hasData.get("count");
                    String hasContent = hasData.get("content");
                    Integer count = Integer.parseInt(hasCount) + 1;
                    hasData.put("count", String.valueOf(count));
                    hasData.put("content", hasContent.concat(page.getElementsByTagName("p").get(i).getTextContent()));
                    parentTagAttrCount.put(String.valueOf(parentTagAttr), hasData);
                    if (count > maxCount) {
                        finalTag = String.valueOf(parentTag);
                        finalClass = String.valueOf(parentClassAttr);
                        maxCount = count;
                    }
                }
//                output.append("P tag parent tag is "+parentTag+ " has attributes " + parentTagAttr + " and content is " + page.getElementsByTagName("p").get(i).getTextContent() + "\n \n \n \n");
            }

            System.out.println("Tag-----"+finalTag +"Class------"+ finalClass +"Count-------"+ maxCount);
            StringBuilder output = new StringBuilder();

            while (!page.getElementsByTagName("style").isEmpty()) {
                page.getElementsByTagName("style").get(0).getParentNode().removeChild(page.getElementsByTagName("style").get(0));
            }


            List<String> images = new ArrayList<String>();
            for (int i=0; i<page.getElementsByTagName(finalTag).getLength(); i++) {
                if (page.getElementsByTagName(finalTag).get(i).getAttributes().getNamedItem("class") != null &&
                        page.getElementsByTagName(finalTag).get(i).getAttributes().getNamedItem("class").getTextContent().equals(finalClass)) {
                    Iterable<DomElement> domChildren = page.getElementsByTagName(finalTag).get(i).getChildElements();
                    List<String> result = new ArrayList<>();
                    for (DomElement dom : domChildren) {

                        String openTagName = "<"+dom.getTagName()+">";
                        String closeTagName = "</"+dom.getTagName()+">";
                        if(isArrayContains(dom.getTagName())) {
                            if (dom.getChildElementCount() > 0) {
                                iterator(dom, output, openTagName, closeTagName, images);
                            } else {
                                output.append(openTagName+dom.getTextContent()+closeTagName);
                            }
                        }
                    }
                }
            }
            String topImageUrl = null;
            HashMap<String, String> additionalDetails = new HashMap<String, String>();
            for (int i=0; i<page.getElementsByTagName("meta").getLength(); i++) {
//                System.out.println(page.getElementsByTagName("meta").get(i).getAttribute("property"));
                if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("og:image") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("property").equals("twitter:image"))
                    topImageUrl = page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent();
                else if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("og:description") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("property").equals("twitter:description") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("name").equals("description"))
                    additionalDetails.put("description", page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent());
                else if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("domain") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("property").equals("og:site_name") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("property").equals("twitter:site_name"))
                    additionalDetails.put("domain", page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent());
                else if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("author") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("name").equals("author"))
                    additionalDetails.put("author", page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent());
                else if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("og:type") ||
                        page.getElementsByTagName("meta").get(i).getAttribute("name").equals("content-type"))
                    additionalDetails.put("type", page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent());
                else if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("article:published_time"))
                    additionalDetails.put("created_at", page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent());
                else if (page.getElementsByTagName("meta").get(i).getAttribute("property").equals("article:modified_time"))
                    additionalDetails.put("modified_at", page.getElementsByTagName("meta").get(i).getAttributes().item(1).getTextContent());
            }

            HashMap<String, Object> returnValue = new HashMap<String, Object>();
            returnValue.put("title", page.getElementsByTagName("title").get(0).getTextContent());
            returnValue.put("content", output.toString());
            returnValue.put("topImageUrl", (topImageUrl == null && !images.isEmpty()) ? images.get(0) : null);
            returnValue.put("additionalDetails", additionalDetails);
            returnValue.put("images", images);
            returnValue.put("url", url);
            return returnValue;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}