package ru.manturov.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private BigDecimal value;
    private Date date;
    private Long fromAccId;
    private Long toAccId;
}
