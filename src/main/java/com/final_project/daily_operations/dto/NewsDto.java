package com.final_project.daily_operations.dto;

import com.final_project.daily_operations.model.News;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
@Component
public class NewsDto {

    private String title;
    private String content;
    private String imageLink;
    private LocalDateTime createDateTime;

    public NewsDto getNewsDtoForHomePage(News news) {
        return new NewsDto(news.getTitle(), news.getContent(), news.getImageLink(), news.getCreateDateTime());
    }

    public List<NewsDto> getListOfNewsDtoForHomePage(List<News> newsList) {
        return newsList.stream()
                .map(this::getNewsDtoForHomePage)
                .collect(Collectors.toList());
    }
}
