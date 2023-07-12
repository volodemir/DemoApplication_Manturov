package ru.manturov.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import ru.manturov.MockSecurityConfiguration;
import ru.manturov.config.SecurityConfiguration;
import ru.manturov.entity.Account;
import ru.manturov.entity.Category;
import ru.manturov.entity.Transaction;
import ru.manturov.entity.User;
import ru.manturov.web.form.TransactionForm;
import ru.manturov.service.AccountService;
import ru.manturov.service.CategoryService;
import ru.manturov.service.RegService;
import ru.manturov.service.TransactionService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.manturov.security.UserRole.USER;

@WebMvcTest(TransactionsController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrUser.class})
@RunWith(SpringRunner.class)
public class TransactionsControllerTest {
    List<Transaction> transactions;
    List<Account> accounts;
    List<Category> categories;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountsService;

    @MockBean
    CategoryService categoryService;

    @MockBean
    TransactionService transactionsService;

    @MockBean
    RegService regService;

    @Before
    public void setUp() throws ParseException {
        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("Qwertasdf99")
                .setRoles(Collections.singleton(USER));

        Account account1 = new Account()
                .setId(1L)
                .setName("Alfa")
                .setBalance(BigDecimal.valueOf(50000))
                .setUser(user);

        Account account2 = new Account()
                .setId(2L)
                .setName("Sber")
                .setBalance(BigDecimal.valueOf(27000))
                .setUser(user);

        accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        Category category1 = new Category()
                .setId(1L)
                .setCategory("Eat")
                .setUser(user);

        Category category2 = new Category()
                .setId(2L)
                .setCategory("Product")
                .setUser(user);

        categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Transaction transaction = new Transaction()
                .setId(1L)
                .setCreatedDate(dateFormat.parse("03.09.2022"))
                .setValue(BigDecimal.valueOf(1000))
                .setFromAccount(account1)
                .setToAccount(account2)
                .setCategories(asList(category1, category2));

        transactions = new ArrayList<>();
        transactions.add(transaction);

        when(regService.getUserById(1L)).thenReturn(user);

        when(transactionsService.findAllByUserId(
                user.getId(),
                user.getId()))
                .thenReturn(transactions);

        when(transactionsService.insert(
                BigDecimal.valueOf(1000),
                account1.getId(),
                account2.getId(),
                asList(category1.getId(), category2.getId())))
                .thenReturn(transaction);

        when(accountsService.getAllByUserId(1L)).thenReturn(accounts);
        when(categoryService.getAllByUserId(1L)).thenReturn(categories);

        BindingResult result = mock(BindingResult.class);
        when(!result.hasErrors()).thenReturn(true);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getTransactions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/transactions"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("transactions", transactions))
                .andExpect(view().name("transactions"));

        verify(transactionsService, times(1)).findAllByUserId(1L, 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getTransactionAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/transactionadd"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("accounts", accounts))
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("transactionForm", new TransactionForm()))
                .andExpect(view().name("transactionadd"));

        verify(accountsService, times(1)).getAllByUserId(1L);
        verify(categoryService, times(1)).getAllByUserId(1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postTransactionAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/transactionadd").accept(MediaType.TEXT_HTML)
                        .param("value", "1000")
                        .param("fromAccountId", "1")
                        .param("toAccountId", "2")
                        .param("categories", "1,2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/web/transactions"));

        verify(transactionsService, times(1)).insert(BigDecimal.valueOf(1000), 1L, 2L, asList(1L, 2L));
    }
}