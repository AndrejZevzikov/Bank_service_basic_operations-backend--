package com.final_project.daily_operations.service;

import com.final_project.daily_operations.exception.NoNewsInDatabase;
import com.final_project.daily_operations.model.News;
import com.final_project.daily_operations.repostory.NewsRepository;
import com.final_project.daily_operations.validation.NewsServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class NewsService {

    public static final String SOMETHING_WENT_WRONG = "Something went wrong, try after few minutes";
    public static final String NO_TITLE = "No Title";
    public static final String GET_LAST_FIVE_NEWS_FROM_DATABASE = "Get last five news from Database";
    private NewsRepository newsRepository;
    private NewsServiceValidation newsServiceValidation;

    public List<News> getNewsByGivenCount(Integer count){
        List<News> lastFiveNews = newsRepository.findLastNewsByGivenCount(count);
        try {
            newsServiceValidation.isAnyNewInDBExists(lastFiveNews);
            log.info(GET_LAST_FIVE_NEWS_FROM_DATABASE);
        } catch (NoNewsInDatabase e) {
            log.error(e.getMessage());
            News emptyNews = News.builder().title(NO_TITLE).content(SOMETHING_WENT_WRONG).build();
            lastFiveNews.add(emptyNews);
        }
        return lastFiveNews;
    }
}
