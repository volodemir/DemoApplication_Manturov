package ru.manturov.api.converter;

import org.springframework.stereotype.Component;
import ru.manturov.api.json.AccountResponse;
import ru.manturov.entity.Account;

@Component
public class AccountToResponseConverter implements Converter<Account, AccountResponse> {

    @Override
    public AccountResponse convert(Account account) {
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }
}
