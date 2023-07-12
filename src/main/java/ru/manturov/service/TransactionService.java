package ru.manturov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manturov.entity.Account;
import ru.manturov.entity.Category;
import ru.manturov.entity.Transaction;
import ru.manturov.exception.NoEntityException;
import ru.manturov.repository.AccountRepository;
import ru.manturov.repository.CategoryRepository;
import ru.manturov.repository.TransactionRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Transaction insert(BigDecimal value, Long fromAccountId, Long toAccountId, List<Long> categories) {
        Transaction transaction = null;
        Account fromAcc;
        Account toAcc;
        BigDecimal changeFromAcc = null;
        BigDecimal changeToAcc;
        if (value != null && (fromAccountId != null || toAccountId != null)) {
            if (fromAccountId != null) {
                fromAcc = accountRepository.findById(fromAccountId).orElseThrow(() -> new NoEntityException(fromAccountId));
                changeFromAcc = fromAcc.getBalance().subtract(value);
                if (changeFromAcc.compareTo(BigDecimal.ZERO) >= 0) {
                    fromAcc.setBalance(changeFromAcc);
                }
            }
            if (toAccountId != null) {
                toAcc = accountRepository.findById(toAccountId).orElseThrow(() -> new NoEntityException(toAccountId));
                changeToAcc = toAcc.getBalance().add(value);
                if (changeFromAcc.compareTo(BigDecimal.ZERO) >= 0) {
                    toAcc.setBalance(changeToAcc);
                }
            }

            transaction = new Transaction();
            List<Category> categoryList = categories.stream().map(c -> categoryRepository.findById(c).orElseThrow(() -> new NoEntityException(c))).collect(Collectors.toList());
            transaction.setValue(value);
            if (fromAccountId != null) {
                transaction.setFromAccount(accountRepository.findById(fromAccountId).orElseThrow(() -> new NoEntityException(fromAccountId)));
            }
            if (toAccountId != null) {
                transaction.setToAccount(accountRepository.findById(toAccountId).orElseThrow(() -> new NoEntityException(toAccountId)));
            }
            transaction.setCategories(categoryList);
            transaction.setCreatedDate(new Date());
            repository.save(transaction);
        }
        return transaction;
    }

    public List<Transaction> findAllByUserId(Long userIdFrom, Long userIdTo) {
        return repository.getAllByFromAccountUserIdOrToAccountUserId(userIdFrom, userIdTo);
    }
}
