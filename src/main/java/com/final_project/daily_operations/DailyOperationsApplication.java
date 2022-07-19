package com.final_project.daily_operations;

import com.final_project.daily_operations.constants.LithuaniaBankApi;
import com.final_project.daily_operations.mapper.mapperService.CurrencyRateXMLMapper;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.model.News;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.repostory.CustomerRepository;
import com.final_project.daily_operations.repostory.NewsRepository;
import com.final_project.daily_operations.helper.CurrencyRateFetchingService;
import com.final_project.daily_operations.util.CurrencyPreparedData;
import com.final_project.daily_operations.util.CustomerPreparedData;
import com.final_project.daily_operations.util.NewsPreparedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DailyOperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyOperationsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(final NewsRepository newsRepository, @Autowired NewsPreparedData newsPreparedData,
                                 final CustomerRepository customerRepository, @Autowired CustomerPreparedData customerPreparedData,
                                 final CurrencyRepository currencyRepository, @Autowired CurrencyPreparedData currencyPreparedData,
                                 final CurrencyRateFetchingService currencyRateFetchingService,
                                 final CurrencyRateXMLMapper currencyRateXMLMapper) {
        return args -> {
            List<Customer> customers = customerPreparedData.setUpCustomers();
            List<News> news = newsPreparedData.setUpNews();
            news.forEach(news1 -> news1.setCustomer(customers.get(0)));
            customers.get(0).setNews(news);
            customerRepository.saveAll(customers);
            currencyRepository.saveAll(currencyPreparedData.setUpCurrencies());
            currencyRateXMLMapper.mapToObj(currencyRateFetchingService.getCurrencyRates(LithuaniaBankApi.LAST_CURRENCY_RATES));
        };
    }
}
