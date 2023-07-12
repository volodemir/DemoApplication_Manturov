package ru.manturov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.manturov.entity.Account;
import ru.manturov.entity.User;
import ru.manturov.security.UserRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository subj;

    @Test
    public void findAllByUserId_UserNotFound() {
        assertTrue(subj.findAllByUserId(2L).isEmpty());
    }

    @Test
    public void findAllByUserId_UserFound() {
        List<Account> accountList = new ArrayList<>();

        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("$2a$10$/MItPFGhSuS83WqAtMQ13OYP5/v.pWocZlppM6g66GsTlVWVC197u")
                .setRoles(Collections.singleton(UserRole.USER))
                .setAccounts(accountList);

        Account accountOne = new Account()
                .setId(1L)
                .setName("AccountName")
                .setBalance(BigDecimal.valueOf(12334533.0))
                .setUser(user);

        Account accountTwo = new Account()
                .setId(2L)
                .setName("AccountName2")
                .setBalance(BigDecimal.valueOf(123.0))
                .setUser(user);

        accountList.add(accountOne);
        accountList.add(accountTwo);

        List<Account> accountsByUserId = subj.findAllByUserId(1L);
        assertNotNull(accountsByUserId);
        assertEquals(accountList.size(), accountsByUserId.size());
        assertEquals(accountList.get(0).getId(), accountsByUserId.get(0).getId());
        assertEquals(accountList.get(0).getName(), accountsByUserId.get(0).getName());
        assertEquals(accountList.get(0).getBalance(), accountsByUserId.get(0).getBalance());
        assertEquals(accountList.get(0).getUser().getEmail(), accountsByUserId.get(0).getUser().getEmail());
        assertEquals(accountList.get(1).getId(), accountsByUserId.get(1).getId());
        assertEquals(accountList.get(1).getName(), accountsByUserId.get(1).getName());
        assertEquals(accountList.get(1).getBalance(), accountsByUserId.get(1).getBalance());
        assertEquals(accountList.get(1).getUser().getEmail(), accountsByUserId.get(1).getUser().getEmail());
    }

    @Test
    public void findById_AccountNotFound() {
        Account account = subj.findById(3L).orElse(null);
        assertNull(account);
    }

    @Test
    public void findById() {
        Account account = subj.findById(1L).orElse(null);
        assertNotNull(account);
        assertEquals(Long.valueOf(1L), account.getId());
        assertEquals(BigDecimal.valueOf(12334533.0), account.getBalance());
        assertEquals("AccountName", account.getName());
    }
}