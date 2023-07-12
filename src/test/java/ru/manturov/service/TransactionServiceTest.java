package ru.manturov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.manturov.entity.Account;
import ru.manturov.entity.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    TransactionService transactionService;

    @Test
    public void getAllByUserId_NoTransaction() {
        when(transactionService.findAllByUserId(5L, 5L)).thenReturn(Collections.emptyList());
        assertTrue(transactionService.findAllByUserId(5L, 5L).isEmpty());
        verify(transactionService, times(1)).findAllByUserId(5L, 5L);
    }

    @Test
    public void getAllByUserId_foundTransaction() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId(29L);
        transaction.setValue(BigDecimal.valueOf(32455));

        transactionList.add(transaction);

        when(transactionService.findAllByUserId(1L, 1L)).thenReturn(transactionList);

        Transaction transaction2 = new Transaction();
        List<Transaction> transaction2List = new ArrayList<>();

        transaction2.setId(29L);
        transaction2.setValue(BigDecimal.valueOf(32455));
        transaction2List.add(transaction2);


        List<Transaction> transactionList2 = transactionService.findAllByUserId(1L, 1L);
        assertNotNull(transactionList2);
        assertEquals(transactionList, transactionList2);

        verify(transactionService, times(1)).findAllByUserId(1L, 1L);
    }

    @Test
    public void insert_notInsert() {
        List<Long> categories = new ArrayList<>();
        categories.add(1L);
        categories.add(2L);

        when(transactionService.insert(null, 1L, 2L, categories)).thenReturn(null);
        assertNull(transactionService.insert(null, 1L, 2L, categories));
        verify(transactionService, times(1)).insert(null, 1L, 2L, categories);
    }

    @Test
    public void insert_inserted() {
        Transaction transaction = new Transaction();
        Date date = new Date();
        transaction.setId(10L);
        transaction.setCreatedDate(date);
        transaction.setValue(BigDecimal.valueOf(4235.35));

        Account fromAcc = new Account();
        fromAcc.setId(1L);
        transaction.setFromAccount(fromAcc);

        Account toAcc = new Account();
        toAcc.setId(2L);
        transaction.setToAccount(toAcc);

        List<Long> categoryList = new ArrayList<>();
        categoryList.add(1L);

        when(transactionService.insert(BigDecimal.valueOf(4235.35), 1L, 2L, categoryList)).thenReturn(transaction);
        assertNotNull(transaction);
        assertEquals(transactionService.insert(BigDecimal.valueOf(4235.35), 1L, 2L, categoryList), transaction);
        verify(transactionService, times(1)).insert(BigDecimal.valueOf(4235.35), 1L, 2L, categoryList);
    }
}
