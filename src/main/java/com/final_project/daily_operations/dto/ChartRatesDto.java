package com.final_project.daily_operations.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChartRatesDto {

    private String type;
    private boolean showInLegend;
    private String name;
    private List<DataPointsDto> dataPoints;
}
