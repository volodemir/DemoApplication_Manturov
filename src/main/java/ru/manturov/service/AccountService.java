package ru.manturov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manturov.entity.Account;
import ru.manturov.entity.User;
import ru.manturov.exception.NoEntityException;
import ru.manturov.repository.AccountRepository;
import ru.manturov.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Account insert(BigDecimal balance, String name, Long userId) {
        Account account = null;
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
        if (user != null) {
            account = new Account();
            account.setBalance(balance);
            account.setName(name);
            account.setUser(user);
            accountRepository.save(account);
        }
        return account;
    }

    public boolean delete(long accountId, long userId) {
        Account account;
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
        if (user != null) {
            account = accountRepository.findById(accountId).orElseThrow(() -> new NoEntityException(accountId));
            if (account != null) {
                accountRepository.delete(account);
                return true;
            }
        }
        return false;
    }

    public Account update(long accountId, String name, BigDecimal balance, long userId) {
        Account account = null;
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
        if (user != null) {
            account = accountRepository.findById(accountId).orElseThrow(() -> new NoEntityException(accountId));
            if (account != null) {
                account.setName(name);
                account.setBalance(balance);
                accountRepository.save(account);
            }
        }
        return account;
    }

    public List<Account> getAllByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }
}
