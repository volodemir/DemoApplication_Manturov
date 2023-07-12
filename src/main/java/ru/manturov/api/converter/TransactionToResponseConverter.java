package ru.manturov.api.converter;

import org.springframework.stereotype.Component;
import ru.manturov.api.json.TransactionResponse;
import ru.manturov.entity.Account;
import ru.manturov.entity.Transaction;

import java.util.Optional;

@Component
public class TransactionToResponseConverter implements Converter<Transaction, TransactionResponse> {

    @Override
    public TransactionResponse convert(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getValue(),
                transaction.getCreatedDate(),
                Optional.ofNullable(transaction.getFromAccount()).map(Account::getId).orElse(null),
                Optional.ofNullable(transaction.getFromAccount()).map(Account::getId).orElse(null));
    }
}