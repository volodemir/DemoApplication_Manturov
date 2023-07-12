package ru.manturov.api.json;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ReportRequest {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate beginDate;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;
}