package ru.manturov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.manturov.entity.Account;
import ru.manturov.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountService accService;

    @Test
    public void insert_accountUserNotFound() {
        assertNull(accService.insert(BigDecimal.valueOf(123421), "Альфа", 1L));
        verify(accService, times(1)).insert(BigDecimal.valueOf(123421), "Альфа", 1L);
    }


    @Test
    public void insert_accountCreated() {
        User user = new User()
                .setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(12312214));
        account.setName("Альфа");
        account.setUser(user);

        when(accService.insert(BigDecimal.valueOf(12312214), "Альфа", 1L)).thenReturn(account);
        assertEquals(accService.insert(BigDecimal.valueOf(12312214), "Альфа", 1L), account);
        verify(accService, times(1)).insert(BigDecimal.valueOf(12312214), "Альфа", 1L);
    }

    @Test
    public void delete_accountUserNotFound() {
        assertFalse(accService.delete(9L, 12L));
        verify(accService, times(1)).delete(9L, 12L);
    }

    @Test
    public void delete_accountDeleted() {
        User user = new User()
                .setId(1L);
        Account account = new Account();
        account.setId(3L);
        account.setUser(user);

        when(accService.delete(3L, 1L)).thenReturn(true);
        assertTrue(accService.delete(3L, 1L));
        verify(accService, times(1)).delete(3L, 1L);
    }

    @Test
    public void getAllByUserId_accountsNotFound() {
        when(accService.getAllByUserId(12L)).thenReturn(Collections.emptyList());
        assertTrue(accService.getAllByUserId(12L).isEmpty());
        verify(accService, times(1)).getAllByUserId(12L);
    }

    @Test
    public void getAllByUserId_accountsFound() {
        User user = new User()
                .setId(20L);

        Account account = new Account();

        List<Account> accountList = new ArrayList<>();
        account.setBalance(BigDecimal.valueOf(23445327));
        account.setUser(user);
        account.setName("Альфа");

        accountList.add(account);

        when(accService.getAllByUserId(20L)).thenReturn(accountList);
        List<Account> accountList2 = accService.getAllByUserId(20L);

        assertEquals(accountList, accountList2);
        verify(accService, times(1)).getAllByUserId(20L);
    }
}
