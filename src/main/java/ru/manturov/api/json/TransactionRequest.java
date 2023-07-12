package ru.manturov.api.json;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class TransactionRequest {

    @NotNull
    @NumberFormat
    private Long id;

    @NumberFormat
    private BigDecimal value;

    @NumberFormat
    private Long fromAccountId;

    @NumberFormat
    private Long toAccountId;

    @NotNull
    private List<Long> categories;
}
