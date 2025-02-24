package com.business.BizNest.controller;

import com.business.BizNest.service.WebScrappingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scrapper")
public class WebScrappingController {

    private final WebScrappingService webScrappingService;

    public WebScrappingController(WebScrappingService webScrappingService) {
        this.webScrappingService = webScrappingService;
    }

    @GetMapping("/title")
    public String getTitle(){
        return webScrappingService.getPageTittle();
    }

    @GetMapping("/headings")
    public List<String> getHeadings(){
        return webScrappingService.getHeadings();
    }

    @GetMapping("/links")
    public List<String> getLinks(){
        return webScrappingService.getLinks();
    }

    @GetMapping("/details")
    public Map<String, Object> getAllDetails(){
        return webScrappingService.getAllDetails();
    }
}
