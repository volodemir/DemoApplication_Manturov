package ru.manturov.api.json;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class CategoryRequest {

    @NotNull
    @NumberFormat
    private long id;

    @NotNull
    @NotEmpty
    private String name;
}
