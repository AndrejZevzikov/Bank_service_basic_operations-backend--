package com.final_project.daily_operations.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Component
public class NewsDto {

    private String title;
    private String content;
    private String imageLink;
    private LocalDateTime createDateTime;
}
