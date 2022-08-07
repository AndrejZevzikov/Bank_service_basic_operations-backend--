package com.final_project.daily_operations.repostory;

import com.final_project.daily_operations.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "SELECT * FROM news n ORDER BY create_date_time DESC LIMIT 5", nativeQuery = true)
    List<News> findLastFiveNews();

    @Modifying
    @Query(value = "SELECT * FROM news n ORDER BY create_date_time DESC LIMIT ?", nativeQuery = true)
    List<News> findLastNewsByGivenCount(Integer count);
}