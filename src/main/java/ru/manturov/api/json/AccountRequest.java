package ru.manturov.api.json;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AccountRequest {

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
