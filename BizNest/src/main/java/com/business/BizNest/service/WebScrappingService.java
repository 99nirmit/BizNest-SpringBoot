package com.business.BizNest.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebScrappingService {

    @Value("${scraper.url}")
    private String url;


    public String getPageTittle(){

        try{
            Document doc =  Jsoup.connect(url).get();
            return doc.title();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching title";
        }
    }

    public List<String> getHeadings(){
        List<String> headingList = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url).get();
            Elements headings = doc.select("h2");

            for(Element heading : headings){
                headingList.add(heading.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return headingList;
    }

    public List<String> getLinks(){
        List<String> linkList = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            for(Element link : links){
                linkList.add(link.attr("href") + " " + link.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkList;
    }

    public Map<String, Object> getAllDetails(){
        Map<String, Object> details = new HashMap<>();

        details.put("title", getPageTittle());
        details.put("headings", getHeadings());
        details.put("links", getLinks());

        return details;
    }

}
