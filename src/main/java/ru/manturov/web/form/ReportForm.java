package ru.manturov.web.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReportForm {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate beginDate;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;
}
