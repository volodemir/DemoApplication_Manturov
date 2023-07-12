package ru.manturov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.manturov.entity.Account;
import ru.manturov.entity.Transaction;
import ru.manturov.entity.User;
import ru.manturov.security.UserRole;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository subj;

    @Test
    public void getAllByFromAccountUserIdOrToAccountUserId_FromAccountUserIdOrToAccountUserIdNotFound() {
        List<Transaction> transactionList = subj.getAllByFromAccountUserIdOrToAccountUserId(2L, 3L);
        assertTrue(transactionList.isEmpty());
    }

    @Test
    public void getAllByFromAccountUserIdOrToAccountUserId_FromAccountUserIdOrToAccountUserIdFound() {
        List<Transaction> transactions = new ArrayList<>();

        List<Account> accountList = new ArrayList<>();

        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("$2a$10$/MItPFGhSuS83WqAtMQ13OYP5/v.pWocZlppM6g66GsTlVWVC197u")
                .setRoles(Collections.singleton(UserRole.USER))
                .setAccounts(accountList);

        Account account = new Account()
                .setId(2L)
                .setName("AccountName2")
                .setBalance(BigDecimal.valueOf(123.0))
                .setUser(user);

        Transaction transactionOne = new Transaction()
                .setId(1L)
                .setValue(BigDecimal.valueOf(32455.0))
                .setFromAccount(account)
                .setToAccount(null)
                .setCreatedDate(new Date(Timestamp.valueOf("2022-09-03 00:00:00").getTime()));

        Transaction transactionTwo = new Transaction()
                .setId(2L)
                .setValue(BigDecimal.valueOf(500.0))
                .setFromAccount(account)
                .setToAccount(null)
                .setCreatedDate(new Date(Timestamp.valueOf("2022-11-03 17:28:00").getTime()));

        Transaction transactionThree = new Transaction()
                .setId(3L)
                .setValue(BigDecimal.valueOf(32455.0))
                .setFromAccount(null)
                .setToAccount(account)
                .setCreatedDate(new Date(Timestamp.valueOf("2022-09-03 00:00:00").getTime()));

        transactions.add(transactionOne);
        transactions.add(transactionTwo);
        transactions.add(transactionThree);

        List<Transaction> transactionList = subj.getAllByFromAccountUserIdOrToAccountUserId(1L, 1L);
        assertNotNull(transactionList);
        assertEquals(transactions.size(), transactionList.size());
        assertEquals(transactions.get(0).getId(), transactionList.get(0).getId());
        assertEquals(transactions.get(0).getValue(), transactionList.get(0).getValue());
        assertEquals(transactions.get(0).getFromAccount().getId(), transactionList.get(0).getFromAccount().getId());
        assertNull(transactionList.get(0).getToAccount());
        assertEquals(transactions.get(0).getCreatedDate(), transactionList.get(0).getCreatedDate());

        assertEquals(transactions.get(1).getId(), transactionList.get(1).getId());
        assertEquals(transactions.get(1).getValue(), transactionList.get(1).getValue());
        assertEquals(transactions.get(1).getFromAccount().getId(), transactionList.get(1).getFromAccount().getId());
        assertNull(transactionList.get(1).getToAccount());
        assertEquals(transactions.get(1).getCreatedDate(), transactionList.get(1).getCreatedDate());

        assertEquals(transactions.get(2).getId(), transactionList.get(2).getId());
        assertEquals(transactions.get(2).getValue(), transactionList.get(2).getValue());
        assertNull(transactionList.get(2).getFromAccount());
        assertEquals(transactions.get(2).getToAccount().getId(), transactionList.get(2).getToAccount().getId());
        assertEquals(transactions.get(2).getCreatedDate(), transactionList.get(2).getCreatedDate());
    }
}