package ru.manturov.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReportResponse {
    private String categoryName;
    private BigDecimal value;
}
