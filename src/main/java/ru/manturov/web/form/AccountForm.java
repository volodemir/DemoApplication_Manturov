package ru.manturov.web.form;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccountForm {

    @NotNull
    @NumberFormat
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NumberFormat
    private BigDecimal balance;
}
