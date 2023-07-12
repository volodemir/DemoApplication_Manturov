package ru.manturov.dao;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ReportModel {
    private String categoryName;
    private BigDecimal value;

    @Override
    public String toString() {
        return categoryName + " " + value;
    }
}
