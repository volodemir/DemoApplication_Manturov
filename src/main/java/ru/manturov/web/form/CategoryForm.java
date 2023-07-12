package ru.manturov.web.form;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {

    @NotNull
    @NumberFormat
    private Long id;

    @NotNull
    @NotEmpty
    private String name;
}
