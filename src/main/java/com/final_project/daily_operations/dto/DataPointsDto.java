package com.final_project.daily_operations.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DataPointsDto {
    private String label;
    private Double y;
    private int x;
}
