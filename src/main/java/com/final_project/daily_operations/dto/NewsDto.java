package com.final_project.daily_operations.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class NewsDto {

    private String title;
    private String content;
    private String imageLink;
    private LocalDateTime createDateTime;
}