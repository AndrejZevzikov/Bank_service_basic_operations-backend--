package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.NewsDto;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.for_controller.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class NewsController {

    private NewsService newsService;
    private MapperDto mapperDto;

    @GetMapping("/{count}") //TODO cash
    public ResponseEntity<List<NewsDto>> getNewsByGivenCount(@PathVariable("count") Integer count){
        return ResponseEntity
                .ok()
                .body(mapperDto.toNewsDtoList(newsService.getNewsByGivenCount(count)));
    }
}
