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
                .imageLink("https://static.vecteezy.com/system/resources/thumbnails/001/997/427/small/business-investment-growth-concept-a-coin-pile-with-a-small-tree-growing-on-a-coin-and-a-hand-holding-a-coin-free-photo.jpg")
                .title("New way to invest – Robo-Advisor")
                .content("Receive personalised investment advice in the mobile app 24/7. It takes only 15 minutes, you can start with as little as 1 euro and no professional financial knowledge is needed.")
                .createDateTime(LocalDateTime.of(2021, 2, 20, 17, 20, 12))
                .build();
        News newsNumber3 = News.builder()
                .imageLink("https://static.theprint.in/wp-content/uploads/2021/10/card-insider-guides-beginners-the-right-way-to-use-a-credit-card.jpg")
                .title("It is all here")
                .content("A debit card is a secure and convenient way of paying for everyday products and services in Lithuania and abroad. A credit card is suitable when you need to borrow money for a short term, buy a desired item, travel abroad or just for daily usage of your own funds.")
                .createDateTime(LocalDateTime.of(2022, 5, 20, 17, 10, 15))
                .build();
        News newsNumber4 = News.builder()
                .imageLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZoSYitBe3wlOXiBcQnzwyr1mrccVuwK3LWA&usqp=CAU")
                .title("A notice about the insured event can be submitted at any unit of the bank")
                .content("In certain cases, e.g., if you are not SEB Internet bank user or do not have qualified signing app Smart-ID, or mobile signature, you can submit a notice about the event at any unit of the bank.")
                .createDateTime(LocalDateTime.of(2022, 6, 18, 17, 5, 12))
                .build();
        News newsNumber5 = News.builder()
                .imageLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5-FM5j4l5Zws5Afbhgq1TvB_00MlyyXEApA&usqp=CAU")
                .title("Save your money for a certain period of time")
                .content("A traditional and safe way of saving\n" +
                        "You may deposit funds for a term of 1 week to 3 years\n" +
                        "It is not possible to make additional contributions or withdrawals during the deposit period")
                .createDateTime(LocalDateTime.of(2022, 1, 20, 17, 23, 20))
                .build();
        News newsNumber6 = News.builder()
                .imageLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0WPM3tH5yaIThuUoQQIgEO4Aoa4aPis4kWA&usqp=CAU")
                .title("Forwarding e-invoices to your clients via bank")
                .content("Forwarding e-invoices to the bank is a convenient and efficient solution to enable your clients to receive an electronically prepared invoice via their internet bank. It is a suitable solution if you provide services to individuals or businesses that pay your company periodically.")
                .createDateTime(LocalDateTime.of(2020, 2, 22, 15, 20, 12))
                .build();
        News newsNumber7 = News.builder()
                .imageLink("https://imageio.forbes.com/specials-images/imageserve/6290d2ae6085acdb167f39fc/Generic-British-High-Street-Banks-Sign--UK-Banks-stopping-account-holders-buying/960x0.jpg?format=jpg&width=960")
                .title("For private banking customers")
                .content("Private banking service covers all financial needs – from daily banking operations to your personal financial management strategy. The main focus is on the structure of your assets and investments, individual financial decisions.")
                .createDateTime(LocalDateTime.of(2021, 8, 2, 19, 20, 12))
                .build();

        newsList.add(newsNumber1);
        newsList.add(newsNumber2);
        newsList.add(newsNumber3);
        newsList.add(newsNumber4);
        newsList.add(newsNumber5);
        newsList.add(newsNumber6);
        newsList.add(newsNumber7);
        return newsList;
    }
}
