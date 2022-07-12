package com.final_project.daily_operations.util;

import com.final_project.daily_operations.model.News;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class NewsPreparedData {

    @Bean
    public List<News> setUpNews() {
        List<News> newsList = new ArrayList<>();
        News newsNumber1 = News.builder()
                .imageLink("https://images.squarespace-cdn.com/content/v1/5acd7385af209683d98390bb/1524188271279-WQTU6L6PBN4JXZ3CIVDD/14324187_10100468163897802_3051081176463297878_o.jpg")
                .title("One step towards your child’s financial independence")
                .content("Start saving for your child by signing up to “For Child Future” until 30th September 2022. In addition we'll cover your child with injury insurance and accident insurance worth EUR 10,000 each.")
                .createDateTime(LocalDateTime.of(2022, 2, 20, 17, 20, 12))
                .build();
        News newsNumber2 = News.builder()
                .imageLink("https://images.squarespace-cdn.com/content/v1/5acd7385af209683d98390bb/1524188271279-WQTU6L6PBN4JXZ3CIVDD/14324187_10100468163897802_3051081176463297878_o.jpg")
                .title("One step towards your child’s financial independence")
                .content("Start saving for your child by signing up to “For Child Future” until 30th September 2022. In addition we'll cover your child with injury insurance and accident insurance worth EUR 10,000 each.")
                .createDateTime(LocalDateTime.of(2021, 2, 20, 17, 20, 12))
                .build();
        News newsNumber3 = News.builder()
                .imageLink("https://images.squarespace-cdn.com/content/v1/5acd7385af209683d98390bb/1524188271279-WQTU6L6PBN4JXZ3CIVDD/14324187_10100468163897802_3051081176463297878_o.jpg")
                .title("One step towards your child’s financial independence")
                .content("Start saving for your child by signing up to “For Child Future” until 30th September 2022. In addition we'll cover your child with injury insurance and accident insurance worth EUR 10,000 each.")
                .createDateTime(LocalDateTime.of(2022, 5, 20, 17, 10, 15))
                .build();
        News newsNumber4 = News.builder()
                .imageLink("https://images.squarespace-cdn.com/content/v1/5acd7385af209683d98390bb/1524188271279-WQTU6L6PBN4JXZ3CIVDD/14324187_10100468163897802_3051081176463297878_o.jpg")
                .title("One step towards your child’s financial independence")
                .content("Start saving for your child by signing up to “For Child Future” until 30th September 2022. In addition we'll cover your child with injury insurance and accident insurance worth EUR 10,000 each.")
                .createDateTime(LocalDateTime.of(2022, 6, 18, 17, 5, 12))
                .build();
        News newsNumber5 = News.builder()
                .imageLink("https://images.squarespace-cdn.com/content/v1/5acd7385af209683d98390bb/1524188271279-WQTU6L6PBN4JXZ3CIVDD/14324187_10100468163897802_3051081176463297878_o.jpg")
                .title("One step towards your child’s financial independence")
                .content("Start saving for your child by signing up to “For Child Future” until 30th September 2022. In addition we'll cover your child with injury insurance and accident insurance worth EUR 10,000 each.")
                .createDateTime(LocalDateTime.of(2022, 1, 20, 17, 23, 20))
                .build();
        News newsNumber6 = News.builder()
                .imageLink("https://images.squarespace-cdn.com/content/v1/5acd7385af209683d98390bb/1524188271279-WQTU6L6PBN4JXZ3CIVDD/14324187_10100468163897802_3051081176463297878_o.jpg")
                .title("One step towards your child’s financial independence")
                .content("Start saving for your child by signing up to “For Child Future” until 30th September 2022. In addition we'll cover your child with injury insurance and accident insurance worth EUR 10,000 each.")
                .createDateTime(LocalDateTime.of(2020, 2, 22, 15, 20, 12))
                .build();

        newsList.add(newsNumber1);
        newsList.add(newsNumber2);
        newsList.add(newsNumber3);
        newsList.add(newsNumber4);
        newsList.add(newsNumber5);
        newsList.add(newsNumber6);
        return newsList;
    }
}
