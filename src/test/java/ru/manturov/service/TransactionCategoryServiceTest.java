package ru.manturov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.manturov.dao.ReportModel;
import ru.manturov.entity.Category;
import ru.manturov.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionCategoryServiceTest {

    @Mock
    CategoryService categoryService;

    @Test
    public void insert_TransactionCategoryNotCreated() {
        when(categoryService.insert("Образование", 5L)).thenReturn(null);
        assertNull(categoryService.insert("Образование", 5L));
        verify(categoryService, times(1)).insert("Образование", 5L);
    }

    @Test
    public void insert_TransactionCategoryCreated() {
        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setUser(user);
        category.setCategory("Образование");

        when(categoryService.insert("Образование", 1L)).thenReturn(category);
        assertEquals(categoryService.insert("Образование", 1L), category);
        verify(categoryService, times(1)).insert("Образование", 1L);
    }

    @Test
    public void update_TransactionNotCategoryUpdated() {
        assertNull(categoryService.update(1L, "Обучение", 1L));
        verify(categoryService, times(1)).update(1L, "Обучение", 1L);
    }

    @Test
    public void update_TransactionCategoryUpdated() {
        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setCategory("Образование");
        category.setUser(user);

        when(categoryService.update(1L, "Образование", 1L)).thenReturn(category);
        assertEquals(categoryService.update(1L, "Образование", 1L), category);
        verify(categoryService, times(1)).update(1L, "Образование", 1L);
    }

    @Test
    public void delete_TransactionCategoryNotDeleted() {
        when(categoryService.delete(1L, 1L)).thenReturn(false);
        assertFalse(categoryService.delete(1L, 1L));
        verify(categoryService, times(1)).delete(1L, 1L);
    }

    @Test
    public void delete_TransactionCategoryDeleted() {
        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setUser(user);

        when(categoryService.delete(1L, 1L)).thenReturn(true);
        assertTrue(categoryService.delete(1L, 1L));
        verify(categoryService, times(1)).delete(1L, 1L);
    }

    @Test
    public void getIncomeByPeriod_AccountNoTransaction() {
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.set(2022, 9, 3);
        endCalendar.set(2022, 9, 9);

        when(categoryService.getIncomeByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime())).thenReturn(Collections.emptyList());
        assertTrue(categoryService.getIncomeByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime()).isEmpty());
        verify(categoryService, times(1)).getIncomeByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime());
    }

    @Test
    public void getIncomeByPeriod_AccountExistTransaction() {
        ReportModel reportModel = new ReportModel();
        reportModel.setCategoryName("category");
        reportModel.setValue(BigDecimal.valueOf(234234));

        List<ReportModel> transactionList = new ArrayList<>();
        transactionList.add(reportModel);

        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.set(2022, 9, 3);
        endCalendar.set(2022, 9, 9);

        when(categoryService.getIncomeByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime())).thenReturn(transactionList);
        assertEquals(categoryService.getIncomeByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime()), transactionList);
        verify(categoryService, times(1)).getIncomeByPeriod(12, beginCalendar.getTime(), endCalendar.getTime());
    }

    @Test
    public void getExpenseByPeriod_AccountNoTransaction() {
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.set(2022, 9, 3);
        endCalendar.set(2022, 9, 9);

        when(categoryService.getExpenseByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime())).thenReturn(Collections.emptyList());
        assertTrue(categoryService.getExpenseByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime()).isEmpty());
        verify(categoryService, times(1)).getExpenseByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime());
    }

    @Test
    public void getExpenseByPeriod_AccountExistTransaction() {
        ReportModel reportModel = new ReportModel();
        reportModel.setCategoryName("category");
        reportModel.setValue(BigDecimal.valueOf(234234));

        List<ReportModel> transactionList = new ArrayList<>();
        transactionList.add(reportModel);

        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.set(2022, 9, 3);
        endCalendar.set(2022, 9, 9);

        when(categoryService.getExpenseByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime())).thenReturn(transactionList);
        assertEquals(categoryService.getExpenseByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime()), transactionList);
        verify(categoryService, times(1)).getExpenseByPeriod(12L, beginCalendar.getTime(), endCalendar.getTime());
    }
}
