package ru.manturov.web.form;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionForm {

    @NumberFormat
    private BigDecimal value;

    @NumberFormat
    @Nullable
    private Long fromAccountId;

    @NumberFormat
    @Nullable
    private Long toAccountId;

    @NotEmpty
    private List<Long> categories;
}
