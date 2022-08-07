package com.final_project.daily_operations.validation;

import com.final_project.daily_operations.exception.NoNewsInDatabase;
import com.final_project.daily_operations.model.News;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsServiceValidation {

    public static final String CAN_T_GET_ANY_NEWS_RECORDS_FROM_DATABASE = "Can't get any news records from database";

    public void isAnyNewInDBExists(final List<News> news) throws NoNewsInDatabase {
        if (news.size() == 0) throw new NoNewsInDatabase(CAN_T_GET_ANY_NEWS_RECORDS_FROM_DATABASE);
    }
}