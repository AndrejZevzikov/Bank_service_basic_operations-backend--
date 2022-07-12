package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.NewsDto;
import com.final_project.daily_operations.model.News;
import com.final_project.daily_operations.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class NewsController {

    private NewsService newsService;
    private NewsDto newsDto;

    @GetMapping("/{count}") //TODO cash
    public ResponseEntity<List<NewsDto>> getNewsByGivenCount(@PathVariable("count") Integer count){
        return ResponseEntity
                .ok()
                .body(newsDto.getListOfNewsDtoForHomePage(newsService.getNewsByGivenCount(count)));
    }
}
